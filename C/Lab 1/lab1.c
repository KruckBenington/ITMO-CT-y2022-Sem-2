#define _CRT_SECURE_NO_WARNINGS

#include "return_codes.h"

#include <malloc.h>
#include <math.h>
#include <stdio.h>

void swap_strings_in_matrix(float **arr, int ind1, int ind2);
void closing_files(FILE *in, FILE *out);
void freeing_matrix(float **arr, int n);
int making_an_answer(float **arr, int n, FILE *out);

int main(int argc, char *argv[])
{
	if (argc != 3)
	{
		printf("Not enough arguments");

		return ERROR_INVALID_DATA;
	}
	char *input = argv[1];
	char *output = argv[2];

	unsigned int n;
	float eps = 0.00001f;

	FILE *in = fopen(input, "r");

	if (!in)
	{
		printf("Input file not found");

		return ERROR_FILE_NOT_FOUND;
	}

	FILE *out = fopen(output, "w");

	if (!out)
	{
		printf("Output file not found");
		fclose(in);
		return ERROR_FILE_NOT_FOUND;
	}

	int res = fscanf(in, "%i", &n);

	if (!res)
	{
		printf("File consist incorrect data");
		closing_files(in, out);
		return ERROR_INVALID_DATA;
	}

	float **arr = malloc(sizeof(float *) * n);

	if (!arr)
	{
		printf("Can't allocate memory");
		closing_files(in, out);
		return ERROR_NOT_ENOUGH_MEMORY;
	}

	for (int i = 0; i < n; i++)
	{
		arr[i] = malloc(sizeof(float) * (n + 1));

		if (!arr[i])
		{
			printf("Can't allocate memory");
			closing_files(in, out);
			free(arr);

			for (int j = 0; j < i; j++)
			{
				free(arr[j]);
			}
			return ERROR_NOT_ENOUGH_MEMORY;
		}
	}

	for (int i = 0; i < n; i++)
	{
		for (int j = 0; j < n + 1; j++)
		{
			float x;
			int res = fscanf(in, "%f", &x);

			if (!res)
			{
				printf("File consist incorrect data");
				closing_files(in, out);
				freeing_matrix(arr, n);

				return ERROR_INVALID_DATA;
			}

			arr[i][j] = x;
		}
	}

	fclose(in);

	for (int i = 0; i < n; i++)
	{
		for (int j = i + 1; j < n; j++)
		{
			if (arr[i][i] == 0)
			{
				if (arr[j][i] == 0)
				{
					continue;
				}

				swap_strings_in_matrix(arr, i, j);
			}

			float coeff = arr[j][i] / arr[i][i];

			arr[j][i] = 0;

			for (int k = 1; k < n + 1; k++)
			{
				if (fabs(arr[j][k] - arr[i][k] * coeff) < eps)
				{
					arr[j][k] = 0;
				}
				else
				{
					arr[j][k] -= arr[i][k] * coeff;
				}
			}
		}
	}

	int check_res = making_an_answer(arr, n, out);

	if (check_res)
	{
		return ERROR_NOT_ENOUGH_MEMORY;
	}

	freeing_matrix(arr, n);
	fclose(out);

	return 0;
}

void swap_strings_in_matrix(float **arr, int ind1, int ind2)
{
	float *c = arr[ind1];
	arr[ind1] = arr[ind2];
	arr[ind2] = c;
}

void closing_files(FILE *in, FILE *out)
{
	fclose(in);
	fclose(out);
}

void freeing_matrix(float **arr, int n)
{
	for (int i = 0; i < n; i++)
	{
		free(arr[i]);
	}

	free(arr);
}

int making_an_answer(float **arr, int n, FILE *out)
{
	float *answer = malloc(sizeof(float) * n);

	if (!answer)
	{
		printf("Can't allocate memory");
		fclose(out);
		freeing_matrix(arr, n);
		return ERROR_NOT_ENOUGH_MEMORY;
	}

	_Bool is_number_solution = 1;

	for (int i = 0; i < n; i++)
	{
		answer[i] = 0;
	}

	for (int i = n - 1; i >= 0; i--)
	{
		float var_value = arr[i][n];

		if (arr[i][i] == 0)
		{
			int k = i + 1;
			float value = 0;

			is_number_solution = 0;

			while (k < n)
			{
				if (arr[i][k] != 0)
				{
					value += arr[i][k] * answer[k];
				}
				k++;
			}

			if (value == arr[i][n])
			{
				fprintf(out, "many solutions");
			}
			else
			{
				fprintf(out, "no solution");
			}

			break;
		}

		for (int j = 0; j < n; j++)
		{
			if (i != j)
			{
				var_value -= arr[i][j] * answer[j];
			}
		}

		answer[i] = var_value / arr[i][i];
	}

	if (is_number_solution)
	{
		for (int i = 0; i < n; i++)
		{
			fprintf(out, "%g\n", answer[i]);
		}

		free(answer);
	}

	return 0;
}