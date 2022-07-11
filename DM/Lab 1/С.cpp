#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>
#include <set>

using namespace std;

int notmain(int argc, char* argv[]) {

	ifstream in("problem3.in");
	ofstream out("problem3.out");

	int n, m, k;
	in >> n >> m >> k;

	set<int> terminals;
	int number;

	for (int i = 0; i < k; i++)
	{
		in >> number;
		terminals.insert(number);
	}

	vector<bool> toTerminal;
	toTerminal.resize(n + 1);

	vector<bool> isCycle;
	toTerminal.resize(n + 1);

	vector<bool> fromStart;
	fromStart.resize(n + 1);

	vector<vector<int>> rightWays;
	rightWays.resize(n);

	vector<vector<int>> reverseWays;
	reverseWays.resize(n);


	

	in.close();
	out.close();

	return 0;
}

