#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>
#include <set>
#include <map>

using namespace std;

bool is_contain_word(string word, char cur_char, int pos, map<char, vector<string>> grammatic);

int cal(int argc, char* argv[]) {

	ifstream in("automaton.in");
	ofstream out("automaton.out");

	int count;
	char begining;

	in >> count >> begining;

	map<char, vector<string>> grammatic;

	char not_terminal;
	string prod, arrow;


	for (size_t i = 0; i < count; i++)
	{
		in >> not_terminal >> arrow >> prod;
		if (grammatic.find(not_terminal) == grammatic.end()) {
			grammatic.insert({ not_terminal, {prod} });
		}
		else {
			grammatic[not_terminal].push_back(prod);
		}
	}


	int m;

	in >> m;
	string word;

	for (size_t i = 0; i < m; i++)
	{
		in >> word;

		if (is_contain_word(word, begining, 0, grammatic)) {
			out << "yes" << endl;
		}
		else {
			out << "no" << endl;
		}

	}

	in.close();
	out.close();

	return 0;
}

bool is_contain_word(string word, char cur_char, int pos, map<char, vector<string>> grammatic) {
	bool result = false;

	if (word.size() == (pos + 1)) {

		vector<string> variants = grammatic[cur_char];

		for (size_t i = 0; i < variants.size(); i++)
		{
			if (variants[i].size() == 1 && variants[i][0] == word[pos]) {
				return true;
			}
		}
	}
	else {

		vector<string> variants = grammatic[cur_char];

		for (size_t i = 0; i < variants.size(); i++)
		{
			if (variants[i].size() > 1 && variants[i][0] == word[pos]) {
				result = is_contain_word(word, variants[i][1], pos + 1, grammatic);
			}

			if (result) {
				break;
			}
		}
	}

	return result;
}