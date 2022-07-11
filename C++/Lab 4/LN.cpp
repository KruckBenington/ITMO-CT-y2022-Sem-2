#include "LN.h"

#include <cstring>
int compareTo(LN number1, LN number2);

ostream& operator<<(ostream& out, LN& ln)
{
	if (ln.sign == 0)
	{
		out << 0;
		return out;
	}

	if (ln.sign == -1)
	{
		out << "-";
	}

	for (long long int i = ln.number.size() - 1; i >= 0; i--)
	{
		out << ln.number[i];
	}

	return out;
}

LN::LN(vector< short > number, char sign, bool is_NaN)
{
	this->number = number;
	this->sign = sign;
	this->is_NAN = is_NaN;
}

LN::LN(long long int num)
{
	if (num != 0L)
	{
		if (num < 0)
		{
			this->sign = -1;
		}
		else
		{
			this->sign = 1;
		}

		while (num != 0L)
		{
			this->number.push_back((num % 10L) * sign);
			num %= 10L;
		}
	}
}

LN::LN(const char* num)
{
	if (strcmp(num, "NaN") == 0)
	{
		LN(true);
	}
	else if (num != NULL)
	{
		if (*num == '-')
		{
			this->sign = -1;
			num++;
		}
		else
		{
			this->sign = 1;
		}

		while (*num != NULL && *num == '0')
		{
			num++;
		}

		if (*num == NULL)
		{
			this->sign = 0;
		}

		while (*num != NULL)
		{
			this->number.push_back(*num - 48);
			num++;
		}

		size_t length = this->number.size();

		for (size_t i = 0; i < length / 2; i++)
		{
			short c = this->number[i];
			this->number[i] = this->number[length - (i + 1)];
			this->number[length - (i + 1)] = c;
		}
	}
	else
	{
	}
}

LN::LN(string_view num)
{
	LN(num.data());
}

LN::LN(LN& num)
{
	this->sign = num.sign;
	this->number = num.number;
	this->is_NAN = num.is_NAN;
}

LN::LN(LN&& num)
{
	this->sign = num.sign;
	this->number = num.number;
	this->is_NAN = num.is_NAN;
	num.sign = 0;
	num.is_NAN = false;
	num.number.clear();
}

LN& LN::operator=(const LN& num_to_copy)
{
	if (&num_to_copy == this)
	{
		return *this;
	}

	if (!this->number.empty())
	{
		this->number.clear();
	}

	this->sign = num_to_copy.sign;
	this->number = num_to_copy.number;
	this->is_NAN = num_to_copy.is_NAN;
	return *this;
}

LN& LN::operator=(LN&& num_to_move)
{
	if (&num_to_move == this)
	{
		return *this;
	}

	if (!this->number.empty())
	{
		this->number.clear();
	}

	this->sign = num_to_move.sign;
	this->number = num_to_move.number;
	this->is_NAN = num_to_move.is_NAN;

	num_to_move.sign = 0;
	num_to_move.number.clear();
	num_to_move.is_NAN = false;

	return *this;
}

bool operator<(LN number1, LN number2)
{
	return (compareTo(number1, number2) == -1);
}

bool operator<=(LN number1, LN number2)
{
	int result = compareTo(number1, number2);
	return (result == -1) || (result == 0);
}

bool operator>(LN number1, LN number2)
{
	return (compareTo(number1, number2) == 1);
}

bool operator>=(LN number1, LN number2)
{
	int result = compareTo(number1, number2);
	return (result == 1) || (result == 0);
}

bool operator==(LN number1, LN number2)
{
	return (compareTo(number1, number2) == 0);
}

bool operator!=(LN number1, LN number2)
{
	return (compareTo(number1, number2) != 0);
}

LN add(LN number1, LN number2)
{
	vector< short > cur_num;
	size_t length;

	short rest = 0;

	if (number1.number.size() > number2.number.size())
	{
		length = number1.number.size();
	}
	else
	{
		length = number2.number.size();
	}

	for (size_t i = 0; i < length; i++)
	{
		short first = i < number1.number.size() ? number1.number[i] : 0;
		short second = i < number2.number.size() ? number2.number[i] : 0;

		cur_num.push_back((first + second + rest) % 10);
		rest = (first + second + rest) / 10;
	}

	if (rest > 0)
	{
		cur_num.push_back(rest);
	}

	return LN(cur_num, number1.sign, false);
}

LN operator+(LN number1, LN number2)
{
	if (number1.is_NAN || number2.is_NAN)
	{
		return LN(true);
	}

	if (number1.sign == 0)
	{
		return number2;
	}
	else if (number2.sign == 0)
	{
		return number1;
	}

	if (number1.sign > number2.sign)
	{
		return number1 - number2;
	}
	else if (number1.sign < number2.sign)
	{
		return number2 - number1;
	}

	return add(number1, number2);
}

void operator+=(LN& number1, LN number2)
{
	number1 = number1 + number2;
}

LN subtract(LN number1, LN number2)
{
	if (number1 == number2)
	{
		return LN();
	}

	vector< short > cur_num;
	size_t length;

	short rest = 0;

	if (number1.number.size() > number2.number.size())
	{
		length = number1.number.size();

		for (size_t i = 0; i < length; i++)
		{
			short first = i < number1.number.size() ? number1.number[i] : 0;
			short second = i < number2.number.size() ? number2.number[i] : 0;

			if (first - second - rest >= 0)
			{
				cur_num.push_back(first - second - rest);
				rest = 0;
			}
			else
			{
				cur_num.push_back(10 + first - second - rest);
				rest = 1;
			}
		}

		long long int i = cur_num.size() - 1;

		while (cur_num[i] == 0)
		{
			cur_num.pop_back();
			i--;
		}

		if (number1.sign == 1)
		{
			return LN(cur_num, 1, false);
		}
		else
		{
			return LN(cur_num, -1, false);
		}
	}
	else
	{
		length = number2.number.size();

		for (size_t i = 0; i < length; i++)
		{
			short first = i < number2.number.size() ? number2.number[i] : 0;
			short second = i < number1.number.size() ? number1.number[i] : 0;

			if (first - second - rest >= 0)
			{
				cur_num.push_back(first - second - rest);
				rest = 0;
			}
			else
			{
				cur_num.push_back(10 + first - second - rest);
				rest = 1;
			}
		}

		long long int i = cur_num.size() - 1;

		while (cur_num[i] == 0)
		{
			cur_num.pop_back();
			i--;
		}

		if (number1.sign == 1)
		{
			return LN(cur_num, -1, false);
		}
		else
		{
			return LN(cur_num, 1, false);
		}
	}
}

LN operator-(LN number1, LN number2)
{
	if (number1.is_NAN || number2.is_NAN)
	{
		return LN(true);
	}

	if (number1.sign == 0)
	{
		return -number2;
	}
	else if (number2.sign == 0)
	{
		return number1;
	}

	if (number1.sign != number2.sign)
	{
		return add(number1, -number2);
	}

	return subtract(number1, number2);
}

LN operator-(LN number)
{
	if (number.is_NAN)
	{
		return LN(true);
	}

	if (number.sign == 0)
	{
		return number;
	}

	return LN(number.number, -number.sign, false);
}

void operator-=(LN& number1, LN number2)
{
	number1 = number1 - number2;
}

LN multiply(LN number1, LN number2)
{
	vector< short > cur_num;
	size_t length1, length2;
	short first, second;
	int mult;

	long long int coef = 0;

	short rest = 0;

	short local_sign = number1.sign * number2.sign;

	LN result = LN();

	if (number1.number.size() > number2.number.size())
	{
		length1 = number1.number.size();
		length2 = number2.number.size();

		for (size_t i = 0; i < length2; i++)
		{
			second = number2.number[i];

			for (size_t k = 0; k < coef; k++)
			{
				cur_num.push_back(0);
			}

			coef++;

			for (size_t j = 0; j < length1; j++)
			{
				first = number1.number[j];

				mult = first * second + rest;
				cur_num.push_back((mult) % 10);
				rest = mult / 10;
			}

			if (rest > 0)
			{
				cur_num.push_back(rest);
			}

			rest = 0;
			result += LN(cur_num, local_sign, false);

			cur_num.clear();
		}
	}
	else
	{
		length1 = number2.number.size();
		length2 = number1.number.size();

		for (size_t i = 0; i < length2; i++)
		{
			second = number1.number[i];

			for (size_t k = 0; k < coef; k++)
			{
				cur_num.push_back(0);
			}

			coef++;

			for (size_t j = 0; j < length1; j++)
			{
				first = number2.number[j];

				mult = first * second + rest;
				cur_num.push_back((mult) % 10);
				rest = mult / 10;
			}

			if (rest > 0)
			{
				cur_num.push_back(rest);
			}

			rest = 0;
			result += LN(cur_num, local_sign, false);

			cur_num.clear();
		}
	}

	return result;
}

LN operator*(LN number1, LN number2)
{
	if (number1.is_NAN || number2.is_NAN)
	{
		return LN(true);
	}

	if (number1.sign == 0 || number2.sign == 0)
	{
		return LN();
	}

	return multiply(number1, number2);
}

void operator*=(LN& number1, LN number2)
{
	number1 = number1 * number2;
}

int compareTo(LN number1, LN number2)
{
	if (number1.is_NAN || number2.is_NAN)
	{
		return 2;
	}

	if (number1.sign > number2.sign)
	{
		return 1;
	}
	else if (number1.sign < number2.sign)
	{
		return -1;
	}
	else if (number1.sign == number2.sign && number1.sign == 0)
	{
		return 0;
	}

	if (number1.number.size() > number2.number.size())
	{
		if (number1.sign >= number2.sign)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}
	else if (number1.number.size() < number2.number.size())
	{
		if (number1.sign >= number2.sign)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}

	long long int length = number1.number.size();

	for (long long int i = length - 1; i >= 0; i--)
	{
		if (number1.number[i] > number2.number[i])
		{
			if (number1.sign >= number2.sign)
			{
				return 1;
			}
			else
			{
				return -1;
			}
		}
		else if (number1.number[i] < number2.number[i])
		{
			if (number1.sign >= number2.sign)
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	}

	return 0;
}
