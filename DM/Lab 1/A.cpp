#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>

using namespace std;

int cal(int argc, char* argv[]) {

	ifstream in("problem1.in");
	ofstream out("problem1.out");

	int n, m, k;

	string word;

	in >> word;
	in >> n >> m >> k;

	vector<vector<pair<int, char>>> v;
	v.resize(n);


	vector<int> terminals;
	terminals.resize(k);

	int number;

	for (int i = 0; i < k; i++)
	{
		in >> terminals[i];
	}

	for (int i = 0; i < m; i++)
	{
		pair<int, char> p;
		in >> number >> p.first >> p.second;
		v[number - 1].push_back(p);
	}

	int cur_q = 1;

	for (int i = 0; i < word.size(); i++)
	{
		vector<pair<int, char>> cur_v;

		if (cur_q > 0) {
			cur_v = v[cur_q - 1];
		}

		if (cur_v.empty() || cur_q == 0) {
			cur_q = 0;
			break;
		}

		char cur_ch = word.at(i);

		for (int j = 0; j < cur_v.size(); j++)
		{
			if (cur_v.at(j).second == cur_ch) {
				cur_q = cur_v.at(j).first;
				break;
			}
			else {
				cur_q = 0;
			}
		}
	}




	bool is_correct = false;

	for (int i = 0; i < terminals.size(); i++)
	{
		if (cur_q == terminals.at(i)) {
			is_correct = true;
			break;
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

