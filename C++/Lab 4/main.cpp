#include "LN.h"
#include "return_codes.h"

#include <fstream>
#include <iostream>
#include <vector>
using namespace std;

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

	vector< LN > stack;
	string data;

	while (in >> data)
	{
		if (data == "_")
		{
			LN first = stack.back();
			stack.pop_back();
			stack.push_back(-first);
			continue;
		}

		LN first, second;

		if (data == "+")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(second + first);
		}
		else if (data == "-")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(second - first);
		}
		else if (data == "*")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(second * first);
		}
		else if (data == "<")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(LN((long long int) (second < first)));
		}
		else if (data == "<=")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(LN((long long int) (second <= first)));
		}
		else if (data == ">")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(LN((long long int) (second > first)));
		}
		else if (data == ">=")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(LN((long long int) (second >= first)));
		}
		else if (data == "==")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(LN((long long int) (second == first)));
		}
		else if (data == "!=")
		{
			first = stack.back();
			stack.pop_back();
			second = stack.back();
			stack.pop_back();
			stack.push_back(LN((long long int) (second != first)));
		}
		else
		{
			stack.push_back(LN(data.data()));
		}
	}

	in.close();

	ofstream out(argv[2]);

	if (!out.is_open())
	{
		fprintf(stderr, "Can't open file to write");
		return ERROR_FILE_NOT_FOUND;
	}

	long long int len = stack.size();

	for (size_t i = 0; i < len; i++)
	{
		out << stack.back() << endl;
		stack.pop_back();
	}

	out.close();

	return 0;
}