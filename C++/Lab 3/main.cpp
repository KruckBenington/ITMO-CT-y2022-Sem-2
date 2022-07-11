#include "phonebook.h"
#include "quicksort.h"
#include "return_codes.h"
#include <fstream>
#include <iostream>
#include <string>
using namespace std;

template< typename T >
int choose_type(bool is_descending, int n, ifstream &in, ofstream &out);

int main(int argc, char *argv[])
{
	if (argc != 3)
	{
		fprintf(stderr, "Not enough arguments");
		return ERROR_INVALID_DATA;
	}

	ifstream in(argv[1]);

	if (!in.is_open())
	{
		fprintf(stderr, "Can't open file to read");
		return ERROR_FILE_NOT_FOUND;
	}

	if (in.eof())
	{
		fprintf(stderr, "Empty file to read");
		return ERROR_INVALID_DATA;
	}

	ofstream out(argv[2]);

	if (!out.is_open())
	{
		fprintf(stderr, "Can't open file to write");
		return ERROR_FILE_NOT_FOUND;
	}

	string data_type;
	string sort_type;
	size_t n;

	in >> data_type >> sort_type >> n;

	bool is_descending = sort_type == "descending" ? true : false;

	if (data_type == "int")
	{
		return choose_type< int >(is_descending, n, in, out);
	}
	else if (data_type == "float")
	{
		return choose_type< float >(is_descending, n, in, out);
	}
	else if (data_type == "phonebook")
	{
		return choose_type< Phonebook >(is_descending, n, in, out);
	}
}

template< typename T >
int choose_type(bool is_descending, int n, ifstream &in, ofstream &out)
{
	T *arr;

	try
	{
		arr = new T[n];
	} catch (const std::bad_alloc &e)
	{
		fprintf(stderr, "Can't allocate memory");
		in.close();
		out.close();
		return ERROR_NOT_ENOUGH_MEMORY;
	}

	for (size_t i = 0; i < n; i++)
	{
		in >> arr[i];
	}
	in.close();

	if (is_descending)
	{
		quicksort< T, true >(arr, 0, n - 1);
	}
	else
	{
		quicksort< T, false >(arr, 0, n - 1);
	}

	for (size_t i = 0; i < n; i++)
	{
		out << arr[i] << endl;
	}

	out.close();
	delete[] arr;

	return 0;
}
