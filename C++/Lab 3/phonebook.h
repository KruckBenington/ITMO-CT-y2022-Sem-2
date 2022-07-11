#include <string>

class Phonebook
{
  public:
	Phonebook(std::string surname, std::string name, std::string patronymic, unsigned long long phone_number);
	Phonebook();

	bool operator<(Phonebook &ph_b);
	bool operator>(Phonebook &ph_b);
	bool operator==(Phonebook &ph_b);

	std::string surname;
	std::string name;
	std::string patronymic;
	unsigned long long phone_number;

	friend std::istream &operator>>(std::istream &in, Phonebook &ph_b);
	friend std::ostream &operator<<(std::ostream &out, Phonebook &ph_b);
};
