#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>
#include <set>
#include <map>

using namespace std;

typedef struct {
	vector<bool> is_terminal;
	vector<map<char, int>> v_to_state;
	vector<set<char>> by_symbols;
	vector<bool> devil;

} Node;

bool is_isomorfism(int x, int y, Node& node_x, Node& node_y, vector<bool>& used_pair_nodes, vector<int>& mapping);

int main(int argc, char* argv[]) {

	ifstream in("isomorphism.in");
	ofstream out("isomorphism.out");

	int n1, m1, k1;
	in >> n1 >> m1 >> k1;

	n1 += 1;
	m1 += 1;
	k1 += 1;

	Node node_1;

	node_1.is_terminal.resize(n1);
	int number;

	for (int i = 1; i < k1; i++)
	{
		in >> number;
		node_1.is_terminal[number] = true;
	}


	node_1.v_to_state.resize(n1);
	node_1.by_symbols.resize(n1);

	int q;
	char c;

	for (int i = 0; i < n1; i++)
	{
		node_1.devil.push_back(true);
	}

	for (int i = 1; i < m1; i++)
	{
		in >> number >> q >> c;
		node_1.v_to_state[number].insert({ c, q });
		node_1.by_symbols[number].insert(c);
		if (number != q) {
			node_1.devil[number] = false;
		}
	}

	//------------

	int n2, m2, k2;
	in >> n2 >> m2 >> k2;

	n2 += 1;
	m2 += 1;
	k2 += 1;

	Node node_2;


	node_2.is_terminal.resize(n2);

	for (int i = 1; i < k2; i++)
	{
		in >> number;
		node_2.is_terminal[number] = true;
	}

	node_2.v_to_state.resize(n2);
	node_2.by_symbols.resize(n2);

	for (int i = 0; i < n2; i++)
	{
		node_2.devil.push_back(true);
	}


	for (int i = 1; i < m2; i++)
	{
		in >> number >> q >> c;
		node_2.v_to_state[number].insert({ c, q });
		node_2.by_symbols[number].insert(c);
		if (number != q) {
			node_2.devil[number] = false;
		}
	}


	vector<bool> used_pair_nodes;
	used_pair_nodes.resize(n1); // meaby need to use mac(n1, n2)



	vector<int> mapping;
	mapping.resize(n1);

	if (is_isomorfism(1, 1, node_1, node_2, used_pair_nodes, mapping)) {
		out << "YES";
	}
	else {
		out << "NO";
	}

	in.close();
	out.close();

	return 0;
}



bool is_isomorfism(int x, int y, Node& node_x, Node& node_y, vector<bool>& used_pair_nodes, vector<int>& mapping) {

	used_pair_nodes[x] = true;

	if (node_x.is_terminal[x] == node_y.is_terminal[y]) {
		mapping[x] = y;

		bool is_valid = true;

		set<char>::iterator iter_symbols = node_x.by_symbols[x].begin();
		for (int i = 0; i < node_x.by_symbols[x].size(); i++)
		{
			char c = *iter_symbols;

			int q_x = node_x.v_to_state[x][c];
			int q_y = node_y.v_to_state[y][c];


			if (node_x.devil[q_x] != node_y.devil[q_y]) {
				return false;
			}

			if (!used_pair_nodes[q_x]) {
				is_valid = is_valid && is_isomorfism(q_x, q_y, node_x, node_y, used_pair_nodes, mapping);
			}
			else {
				is_valid = is_valid && (mapping[q_x] == q_y);
			}

		}

		return is_valid;

	}

	return false;

}

