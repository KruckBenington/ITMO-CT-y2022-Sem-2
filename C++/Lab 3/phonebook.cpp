#include "phonebook.h"
#include <iostream>
using namespace std;

int compareTo(Phonebook& ph_b1, Phonebook& ph_b2);

Phonebook::Phonebook(string surname, string name, string patronymic, unsigned long long phone_number)
{
	this->surname = surname;
	this->name = name;
	this->patronymic = patronymic;
	this->phone_number = phone_number;
}

Phonebook::Phonebook() {}

bool Phonebook::operator<(Phonebook& ph_b)
{
	return (compareTo(*this, ph_b) == 1);
}

bool Phonebook::operator>(Phonebook& ph_b)
{
	return (compareTo(*this, ph_b) == 2);
}

bool Phonebook::operator==(Phonebook& ph_b)
{
	return (compareTo(*this, ph_b) == 0);
}

int compareTo(Phonebook& ph_b1, Phonebook& ph_b2)
{
	if (ph_b1.surname < ph_b2.surname)
	{
		return 1;
	}
	else if (ph_b1.surname > ph_b2.surname)
	{
		return 2;
	}

	if (ph_b1.name < ph_b2.name)
	{
		return 1;
	}
	else if (ph_b1.name > ph_b2.name)
	{
		return 2;
	}

	if (ph_b1.patronymic < ph_b2.patronymic)
	{
		return 1;
	}
	else if (ph_b1.patronymic > ph_b2.patronymic)
	{
		return 2;
	}

	if (ph_b1.phone_number < ph_b2.phone_number)
	{
		return 1;
	}
	else if (ph_b1.phone_number > ph_b2.phone_number)
	{
		return 2;
	}

	return 0;
}

std::istream& operator>>(std::istream& in, Phonebook& ph_b)
{
	in >> ph_b.surname;
	in >> ph_b.name;
	in >> ph_b.patronymic;
	in >> ph_b.phone_number;

	return in;
}

std::ostream& operator<<(std::ostream& out, Phonebook& ph_b)
{
	out << ph_b.surname << " " << ph_b.name << " " << ph_b.patronymic << " " << ph_b.phone_number;

	return out;
}
