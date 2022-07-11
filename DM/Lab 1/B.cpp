#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>
#include <set>

using namespace std;

int poop(int argc, char* argv[]) {

	ifstream in("problem2.in");
	ofstream out("problem2.out");

	int n, m, k;

	string word;

	in >> word;
	in >> n >> m >> k;
	vector<vector<pair<int, char>>> v;
	v.resize(n);


	set<int> terminals;

	int number;

	for (int i = 0; i < k; i++)
	{
		in >> number;
		terminals.insert(number);
	}

	for (int i = 0; i < m; i++)
	{
		pair<int, char> p;
		in >> number >> p.first >> p.second;
		v[number - 1].push_back(p);
	}

	vector<set<int>> qs;
	qs.resize(word.size() + 1);
	qs[0].insert(1);

	vector<pair<int, char>> cur_v;
	char cur_ch;
	bool is_answer = true;

	for (int i = 0; i < word.size(); i++)
	{
		cur_ch = word[i];

		set<int>::iterator iter_q = qs[i].begin();

		int lenght = qs[i].size();

		if (lenght == 0) {
			is_answer == false;
			break;
		}

		for (int k = 0; k < qs[i].size(); k++)
		{

			cur_v = v[*iter_q - 1];


			if (cur_v.empty()) {
				continue;
			}

			for (int j = 0; j < cur_v.size(); j++)
			{
				if (cur_v[j].second == cur_ch) {

					qs[i + 1].insert(cur_v[j].first);
				}
			}

			iter_q++;
		}
	}





	bool is_correct = false;
	set<int>::iterator iter_q = qs[word.size()].begin();

	if (is_answer) {
		for (int i = 0; i < qs[word.size()].size(); i++)
		{
			if (terminals.find(*iter_q) != terminals.end()) {
				is_correct = true;
				break;
			}

			iter_q++;
		}
	}



	if (is_correct) {
		out << "Accepts";
	}
	else {
		out << "Rejects";
	}

	in.close();
	out.close();

	return 0;
}

