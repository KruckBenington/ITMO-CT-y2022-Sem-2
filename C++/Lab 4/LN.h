#include <string_view>

#include <iostream>
#include <vector>
using namespace std;

class LN
{
  private:
	short sign = 0;
	vector< short > number;
	bool is_NAN = false;
	friend int compareTo(LN number1, LN number2);
	friend LN add(LN number1, LN number2);
	friend LN subtract(LN number1, LN number2);
	friend LN multiply(LN number1, LN number2);

  public:
	LN(bool is_NaN) { is_NAN = true; };
	LN(vector< short > number, char sign, bool is_NaN);
	LN(){};
	LN(long long int num);
	LN(const char *num);
	LN(string_view num);
	LN(LN &num);
	LN(LN &&num);

	LN &operator=(const LN &num_to_copy);
	LN &operator=(LN &&num_to_move);

	friend bool operator<(LN number1, LN number2);
	friend bool operator<=(LN number1, LN number2);
	friend bool operator>(LN number1, LN number2);
	friend bool operator>=(LN number1, LN number2);
	friend bool operator==(LN number1, LN number2);
	friend bool operator!=(LN number1, LN number2);

	friend LN operator+(LN number1, LN number2);
	friend void operator+=(LN &number1, LN number2);
	friend LN operator-(LN number1, LN number2);
	friend void operator-=(LN &number1, LN number2);
	friend LN operator-(LN number);
	friend LN operator*(LN number1, LN number2);
	friend void operator*=(LN &number1, LN number2);

	friend ostream &operator<<(ostream &out, LN &ln);
};