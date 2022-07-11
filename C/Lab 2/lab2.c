#define _CRT_SECURE_NO_WARNINGS

#include "return_codes.h"

#include <malloc.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>

#define ZLIB

#if defined(ZLIB)
	#include <zlib.h>
#elif defined(LIBDEFLATE)
	#include <libdeflate.h>
#elif defined(ISAL)
	#include <include/igzip_lib.h>
#else
	#error "This library isn't supported"
#endif

typedef unsigned char byte_h;
typedef unsigned int uint;

typedef struct PNG_properties
{
	FILE *png;
	uint width;
	uint height;
	uint last_data_length;
	uint arr_length;
	uint cur_data_length;
	byte_h colour_type;
	byte_h is_IEND;
	byte_h is_IDAT;
	byte_h is_IHDR;
	byte_h is_PLTE;
	byte_h *data;

} png_prop;

const byte_h IHDR_type[4] = { 73, 72, 68, 82 };
const byte_h IHDR_length[4] = { 0, 0, 0, 13 };
const byte_h IHDR_tr[3] = { 0, 0, 0 };
const byte_h PLTE_type[4] = { 80, 76, 84, 69 };
const byte_h IDAT_type[4] = { 73, 68, 65, 84 };
const byte_h IEND_type[4] = { 73, 69, 78, 68 };
const byte_h PNG_signature[8] = {
	137, 80, 78, 71, 13, 10, 26, 10,
};

byte_h is_not_PNG(FILE *png);
byte_h read_IHDR(png_prop *png_sig);
byte_h check_chunck(FILE *png, byte_h *signature, int length);
byte_h clear_stream(FILE *png, uint length);
byte_h skip_all_to_IDAT(png_prop *png_sig);
byte_h read_IDAT_data(png_prop *png_sig);
byte_h read_header(png_prop *png_sig);
byte_h read_IDATs(png_prop *png_sig);
byte_h skip_all_to_IEND(png_prop *png_sig);
byte_h convert_to_pnm(png_prop *png_sig, char *output);
void error_message(char *message);
uint conv_to_int(byte_h *pntr, uint length);
byte_h predictor(byte_h a, byte_h b, byte_h c);
void flags_to_NULL(png_prop *png_sig);

int main(int argc, char *argv[])
{
	png_prop png_sig;
	png_sig.arr_length = 0;
	png_sig.data = NULL;
	png_sig.cur_data_length = 0;
	flags_to_NULL(&png_sig);

	if (argc != 3)
	{
		error_message("Not enough arguments");
		return ERROR_INVALID_DATA;
	}

	char *input = argv[1];
	char *output = argv[2];

	png_sig.png = fopen(input, "rb");

	if (!png_sig.png)
	{
		error_message("Input file not found");
		return ERROR_FILE_NOT_FOUND;
	}

	uint r = is_not_PNG(png_sig.png);

	if (r)
	{
		fclose(png_sig.png);
		return r;
	}

	r = read_IHDR(&png_sig);

	if (r)
	{
		fclose(png_sig.png);
		return r;
	}

	r = skip_all_to_IDAT(&png_sig);

	if (r)
	{
		fclose(png_sig.png);
		return r;
	}

	r = read_IDATs(&png_sig);

	if (r)
	{
		fclose(png_sig.png);
		return r;
	}

	r = skip_all_to_IEND(&png_sig);

	if (r)
	{
		free(png_sig.data);
		fclose(png_sig.png);
		return r;
	}

	r = convert_to_pnm(&png_sig, output);

	if (r)
	{
		free(png_sig.data);
		fclose(png_sig.png);
		return r;
	}

	free(png_sig.data);
	fclose(png_sig.png);

	return 0;
}

byte_h is_not_PNG(FILE *png)
{
	byte_h r = check_chunck(png, PNG_signature, 8);

	if (r)
	{
		error_message("Wrong PNG signature");
	}

	return r;
}

void error_message(char *message)
{
	fprintf(stderr, message);
}

byte_h check_chunck(FILE *png, byte_h *signature, int length)
{
	byte_h data[8];

	const byte_h t = fread(data, sizeof(byte_h), length, png);

	if (t != length || memcmp(signature, data, length))
	{
		return ERROR_INVALID_DATA;
	}

	return 0;
}

byte_h read_IHDR(png_prop *png_sig)
{
	byte_h res = check_chunck(png_sig->png, IHDR_length, 4);

	if (res)
	{
		error_message("Wrong IHDR length");
		return res;
	}

	res = check_chunck(png_sig->png, IHDR_type, 4);

	if (res)
	{
		error_message("Wrong IHDR type");
		return res;
	}

	byte_h data[17];

	res = fread(data, sizeof(byte_h), 17, png_sig->png);

	if (res != 17)
	{
		error_message("There is no IHDR chunk");
		return ERROR_INVALID_DATA;
	}

	byte_h *data_pntr = data;

	png_sig->width = conv_to_int(data_pntr, 4);
	png_sig->height = conv_to_int(data_pntr + 4, 4);

	if (data_pntr[8] != 8)
	{
		error_message("Wrong IHDR bit depth");
		return ERROR_INVALID_DATA;
	}
	png_sig->colour_type = data_pntr[9];

	if (!png_sig->width || !png_sig->height)
	{
		error_message("Zero size of png picture");
		return ERROR_INVALID_DATA;
	}

	if (png_sig->colour_type != 0 && png_sig->colour_type != 2)
	{
		error_message("Wrong colour type");
		return ERROR_INVALID_DATA;
	}

	if (memcmp(data_pntr + 10, IHDR_tr, 3))
	{
		error_message("Wrong IHDR compression method / filter method / interlace method");
		return ERROR_INVALID_DATA;
	}

	return 0;
}

uint conv_to_int(byte_h *pntr, uint length)
{
	pntr = pntr + (length - 1);
	uint res = 0;
	uint hex = 1;
	for (uint i = 0; i < length; i++)
	{
		res += (*pntr--) * hex;
		hex *= 256;
	}
	return res;
}

byte_h skip_all_to_IDAT(png_prop *png_sig)
{
	do
	{
		byte_h r = read_header(png_sig);

		if (r)
		{
			return r;
		}

		if (png_sig->is_IDAT)
		{
			return 0;
		}
		else if (png_sig->is_IHDR)
		{
			error_message("Not expceted second IHDR");
			return ERROR_INVALID_DATA;
		}
		else if (png_sig->is_IEND)
		{
			error_message("Wrong png structure: IEND without IDAT");
			return ERROR_INVALID_DATA;
		}
		else if (png_sig->is_PLTE)
		{
			if (png_sig->colour_type == 0 || png_sig->last_data_length % 3 != 0 || png_sig->last_data_length == 0 ||
				png_sig->last_data_length > 256 * 3)
			{
				error_message("Wrong PLTE chunk");
				return ERROR_INVALID_DATA;
			}

			r = read_header(png_sig);

			if (r)
			{
				return r;
			}

			if (!png_sig->is_IDAT)
			{
				error_message("Expected IDAT chunk after PLTE");
				return ERROR_INVALID_DATA;
			}

			return 0;
		}

		r = clear_stream(png_sig->png, png_sig->last_data_length + 4);

		if (r)
		{
			return r;
		}
	} while (1);

	return 0;
}

byte_h clear_stream(FILE *png, uint length)
{
	if (fseek(png, length, SEEK_CUR) != 0)
	{
		error_message("Not expected end of file");
		return ERROR_INVALID_DATA;
	}

	return 0;
}

byte_h read_IDATs(png_prop *png_sig)
{
	while (png_sig->is_IDAT)
	{
		byte_h r = read_IDAT_data(png_sig);

		if (r)
		{
			return r;
		}

		r = read_header(png_sig);

		if (r)
		{
			free(png_sig->data);
			return r;
		}
	}

	return 0;
}

byte_h read_header(png_prop *png_sig)
{
	byte_h length_arr[8];

	if (fread(length_arr, sizeof(byte_h), 8, png_sig->png) != 8)
	{
		error_message("Broken header of chunk");
		return ERROR_INVALID_DATA;
	}

	byte_h *data_pntr = length_arr;

	png_sig->last_data_length = conv_to_int(data_pntr, 4);

	data_pntr += 4;

	flags_to_NULL(png_sig);

	if (!memcmp(data_pntr, IDAT_type, 4))
	{
		png_sig->is_IDAT = 1;
	}
	else if (!memcmp(data_pntr, IEND_type, 4))
	{
		png_sig->is_IEND = 1;
	}
	else if (!memcmp(data_pntr, IHDR_type, 4))
	{
		png_sig->is_IHDR = 1;
	}
	else if (!memcmp(data_pntr, PLTE_type, 4))
	{
		png_sig->is_PLTE = 1;
	}
	else
	{
		if ('A' <= *data_pntr && *data_pntr <= 'Z')
		{
			error_message("Wrong chunk: Not critical cnunk, but begins on capital letter");
			return ERROR_INVALID_DATA;
		}
	}

	return 0;
}

void flags_to_NULL(png_prop *png_sig)
{
	png_sig->is_IDAT = 0;
	png_sig->is_IEND = 0;
	png_sig->is_IHDR = 0;
	png_sig->is_PLTE = 0;
}

byte_h read_IDAT_data(png_prop *png_sig)
{
	png_sig->cur_data_length += png_sig->last_data_length;
	uint r;

	if (png_sig->arr_length == 0)
	{
		png_sig->arr_length = (png_sig->cur_data_length) * 2;
		png_sig->data = malloc(sizeof(byte_h) * png_sig->arr_length);

		if (!png_sig->data)
		{
			error_message("Can't allocate memory");
			return ERROR_NOT_ENOUGH_MEMORY;
		}
	}

	if (png_sig->cur_data_length > png_sig->arr_length)
	{
		png_sig->arr_length = (png_sig->cur_data_length) * 2;
		byte_h *data2 = realloc(png_sig->data, png_sig->arr_length);

		if (!data2)
		{
			free(png_sig->data);
			error_message("Can't allocate memory");
			return ERROR_NOT_ENOUGH_MEMORY;
		}
		png_sig->data = data2;
	}

	byte_h *data_pnrt = png_sig->data;
	data_pnrt += png_sig->cur_data_length - png_sig->last_data_length;

	r = fread(data_pnrt, sizeof(byte_h), png_sig->last_data_length, png_sig->png);

	if (r != png_sig->last_data_length)
	{
		free(png_sig->data);
		error_message("Wrong IDAT chunk");
		return ERROR_INVALID_DATA;
	}

	r = clear_stream(png_sig->png, 4);

	if (r)
	{
		free(png_sig->data);
		return r;
	}

	return 0;
}

byte_h skip_all_to_IEND(png_prop *png_sig)
{
	uint r;

	while (!png_sig->is_IEND)
	{
		r = clear_stream(png_sig->png, png_sig->last_data_length + 4);

		if (r)
		{
			return r;
		}

		r = read_header(png_sig);

		if (r)
		{
			return r;
		}

		if (png_sig->is_IHDR || png_sig->is_IDAT || png_sig->is_PLTE)
		{
			error_message("Wrong chank sequence : not expected IHDR or IDAT or PLTE");
			return ERROR_INVALID_DATA;
		}
	}

	if (png_sig->last_data_length != 0)
	{
		error_message("Wrong IEND chunck length");
		return ERROR_INVALID_DATA;
	}

	r = clear_stream(png_sig->png, 4);

	if (r)
	{
		return r;
	}

	byte_h end[1];

	if (fread(end, sizeof(byte_h), 1, png_sig->png) == 1)
	{
		error_message("Expected end of file after IEND chunk");
		return ERROR_INVALID_DATA;
	}

	return 0;
}

byte_h convert_to_pnm(png_prop *png_sig, char *output)
{
	const byte_h shift = png_sig->colour_type + 1;
	uint uncompressed_data_length = (png_sig->height * (png_sig->width * (shift) + 1));

	byte_h *arr = malloc(sizeof(byte_h) * uncompressed_data_length);

	if (!arr)
	{
		error_message("Can't allocate memory");
		return ERROR_NOT_ENOUGH_MEMORY;
	}

#if defined(ZLIB)
	byte_h r = uncompress(arr, &uncompressed_data_length, png_sig->data, png_sig->cur_data_length);
#elif defined(LIBDEFLATE)
	struct libdeflate_decompressor *dcmprs = libdeflate_alloc_decompressor();
	if (!dcmprs)
	{
		free(arr);
		error_message("Can't allocate a decompressor");
		return ERROR_OUTOFMEMORY;
	}
	byte_h r = libdeflate_zlib_decompress(dcmprs, png_sig->data, png_sig->cur_data_length, arr, uncompressed_data_length, NULL);
	libdeflate_free_decompressor(dcmprs);
#elif defined(ISAL)
	struct inflate_state dcmprs_prop;
	isal_inflate_init(&dcmprs_prop);
	dcmprs_prop.next_in = png_sig->data;
	dcmprs_prop.avail_in = png_sig->cur_data_length;
	dcmprs_prop.next_out = arr;
	dcmprs_prop.avail_out = uncompressed_data_length;
	dcmprs_prop.crc_flag = ISAL_ZLIB;
	byte_h r = isal_inflate(&dcmprs_prop);
#endif

	uint raw_length = png_sig->width * shift + 1;

	if (r)
	{
		free(arr);
		error_message("Error in uncompressing");
		return ERROR_INVALID_DATA;
	}

	byte_h *cur_el;
	byte_h a;
	byte_h b;
	byte_h c;
	byte_h filter;

	for (size_t i = 0; i < png_sig->height; i++)
	{
		filter = arr[i * raw_length];

		for (size_t j = 1; j < raw_length; j++)
		{
			cur_el = &arr[i * raw_length + j];
			a = (j > shift) ? arr[i * raw_length + j - shift] : 0;
			b = (i > 0) ? arr[(i - 1) * raw_length + j] : 0;
			c = ((i > 0) && (j > shift)) ? arr[(i - 1) * raw_length + j - shift] : 0;

			if (filter == 0)
			{
				continue;
			}
			else if (filter == 1)
			{
				*cur_el += a;
				
			}
			else if (filter == 2)
			{
				*cur_el += b;
			}
			else if (filter == 3)
			{
				*cur_el += (a + b) / 2;
			}
			else if ((filter == 4))
			{
				*cur_el += predictor(a, b, c);
			}
			else
			{
				free(arr);
				error_message("Wrong filter");
				return ERROR_INVALID_DATA;
			}
		}
	}

	FILE *pnm = fopen(output, "wb");

	if (!pnm)
	{
		free(arr);
		error_message("Input file not found");
		return ERROR_FILE_NOT_FOUND;
	}

	if (png_sig->colour_type == 0)
	{
		fprintf(pnm, "P5\n%i %i\n255\n", png_sig->width, png_sig->height);
	}
	else
	{
		fprintf(pnm, "P6\n%i %i\n255\n", png_sig->width, png_sig->height);
	}

	for (size_t i = 0; i < png_sig->height; i++)
	{
		uint e = fwrite(arr + i * raw_length + 1, sizeof(byte_h), raw_length - 1, pnm);

		if (e != raw_length - 1)
		{
			free(arr);
			fclose(pnm);
			error_message("Can't read png uncompressed data array");
			return ERROR_INVALID_DATA;
		}
	}

	free(arr);
	fclose(pnm);

	return 0;
}

byte_h predictor(byte_h a, byte_h b, byte_h c)
{
	int p = a + b - c;
	int pa = abs(p - a);
	int pb = abs(p - b);
	int pc = abs(p - c);

	if ((pa <= pb) && (pa <= pc))
	{
		return a;
	}
	else if (pb <= pc)
	{
		return b;
	}
	else
	{
		return c;
	}
}