#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>
#include <set>
#include <map>
#include <sstream>  
#include <algorithm>  

using namespace std;

int main(int argc, char* argv[]) {

	ifstream in("epsilon.in");
	ofstream out("epsilon.out");

	int count;
	char begining;

	in >> count >> begining;

	vector<pair<char, string>> grammatic;
	set<char> eps_prod;
	vector<char> answer;

	char not_terminal;
	string prod, arrow, line, to_not_terminal;

	bool is_last = false;

	getline(in, line);

	for (size_t i = 0; i < count; i++)
	{
		
		getline(in, line);

		stringstream line_parse(line);

		getline(line_parse, to_not_terminal, ' ');
		not_terminal = to_not_terminal[0];

		if (line.size() > 5) {
			getline(line_parse, arrow, ' ');
			getline(line_parse, prod);

			grammatic.push_back({ not_terminal, prod });
		}
		else {
			eps_prod.insert(not_terminal);
			answer.push_back(not_terminal);
		}
	}



	
	int i = 0;

	while (i < grammatic.size()) {

		if (eps_prod.find(grammatic[i].first) == eps_prod.end()) {

			string word = grammatic[i].second;
			bool is_eps = true;


			for (char ch : word)
			{
				if (eps_prod.find(ch) == eps_prod.end()) {
					is_eps = false;
					break;
				}
			}
			
			if (is_eps) {
				answer.push_back(grammatic[i].first);
				eps_prod.insert(grammatic[i].first);
				i = 0;
			}
		}

		i++;
	}

	sort(answer.begin(), answer.end());

	for (size_t i = 0; i < answer.size(); i++)
	{
		out << answer[i] << ' ';
	}

	return 0;
}