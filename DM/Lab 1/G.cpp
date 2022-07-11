#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>
#include <set>
#include <map>
#include <queue>

using namespace std;

int fofo(int argc, char* argv[]) {

	ifstream in("equivalence.in");
	ofstream out("equivalence.out");

	int n1, m1, k1;
	in >> n1 >> m1 >> k1;

	n1 += 1;
	m1 += 1;
	k1 += 1;

	vector<bool> is_terminal1;
	is_terminal1.resize(n1);
	int number;

	for (int i = 1; i < k1; i++)
	{
		in >> number;
		is_terminal1[number] = true;
	}


	vector<map<char, int>> v1;
	v1.resize(n1);

	int q;
	char c;

	for (int i = 1; i < m1; i++)
	{
		in >> number >> q >> c;
		v1[number].insert({ c, q });
	}

	//------------

	int n2, m2, k2;
	in >> n2 >> m2 >> k2;

	n2 += 1;
	m2 += 1;
	k2 += 1;


	vector<bool> is_terminal2;
	is_terminal2.resize(n2);

	for (int i = 1; i < k2; i++)
	{
		in >> number;
		is_terminal2[number] = true;
	}

	vector<map<char, int>> v2;
	v2.resize(n2);


	for (int i = 1; i < m2; i++)
	{
		in >> number >> q >> c;
		v2[number].insert({ c, q });
	}




	vector<vector<bool>> used_pair_nodes;
	used_pair_nodes.resize(n1);

	for (int i = 0; i < n1; i++)
	{
		used_pair_nodes[i].resize(n2);
	}

	queue<pair<int, int>> nodes;
	nodes.push(make_pair(1, 1));

	bool is_equivalent = true;

	while (!nodes.empty()) {

		pair<int, int> cur_pair_nodes = nodes.front();
		nodes.pop();

		if (is_terminal1[cur_pair_nodes.first] == is_terminal2[cur_pair_nodes.second]) {

			used_pair_nodes[cur_pair_nodes.first][cur_pair_nodes.second] = true;
			char symbol = 'a';

		

			for (int i = 0; i < 26; i++)
			{

				int x, y;

				if (v1[cur_pair_nodes.first].find(symbol + i) == v1[cur_pair_nodes.first].end()) {
					x = 0;
				}
				else {
					x = v1[cur_pair_nodes.first][symbol + i];
				} 

				if(v2[cur_pair_nodes.second].find(symbol + i) == v2[cur_pair_nodes.second].end()) {
					y = 0;
				}
				else
				{
					y = v2[cur_pair_nodes.second][symbol + i];
				}


			
				

				if (!used_pair_nodes[x][y]) {
					nodes.push(make_pair(x, y));
				}
			}

			
		}
		else {
			is_equivalent = false;
			break;
		}
	}


	if (is_equivalent) {
		out << "YES";
	}
	else {
		out << "NO";
	}


	in.close();
	out.close();

	return 0;
}

