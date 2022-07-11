#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <utility>
#include <set>
#include <map>
#include <queue>

using namespace std;

typedef long long ll;
const ll MOD = 1000000000 + 7;

vector<vector<ll>> square_matrix(vector<vector<ll>> v1, vector<vector<ll>> v2, vector<vector<ll>> dop);
vector<vector<ll>> fast_pow_matrix(vector<vector<ll>> v, vector<vector<ll>> dop, ll n);

int super_not_main(int argc, char* argv[]) {

	ifstream in("problem5.in");
	ofstream out("problem5.out");

	ll n, m, k, l;
	in >> n >> m >> k >> l;

	n = n + 1;
	m = m + 1;
	k = k + 1;

	set<ll> nka_terminals;
	ll number, to_statement;
	char symbol;

	for (ll i = 1; i < k; i++)
	{
		in >> number;
		nka_terminals.insert(number);
	}

	vector<map<char, set<ll>>> nka_transitions;
	nka_transitions.resize(n);

	for (ll i = 1; i < m; i++)
	{
		in >> number >> to_statement >> symbol;
		if (nka_transitions[number].find(symbol) == nka_transitions[number].end()) {
			nka_transitions[number].insert({ symbol, {to_statement} });
		}
		else {
			nka_transitions[number][symbol].insert(to_statement);
		}
	}



	queue<set<ll>> q_set;
	q_set.push({ 1 });

	set<set<ll>> dka_nodes;
	dka_nodes.insert({ 1 });


	vector<set<ll>> usefull_dka_nodes;
	usefull_dka_nodes.resize(1);
	usefull_dka_nodes.push_back({ 1 });

	map<set<ll>, vector<set<ll>>> dka_transitons;


	char start_symbol = 'a';

	while (!q_set.empty()) {
		set<ll> cur_nodes_set = q_set.front();
		q_set.pop();
		ll set_len = cur_nodes_set.size();
		set<ll> new_nodes_set;

		for (char c = 0; c < 26; c++)
		{

			set<ll>::iterator iter_s = cur_nodes_set.begin();
			char cur_symbol = start_symbol + c;

			for (ll i = 0; i < set_len; i++)
			{
				if (nka_transitions[*iter_s].find(cur_symbol) != nka_transitions[*iter_s].end()) {
					new_nodes_set.insert(nka_transitions[*iter_s][cur_symbol].begin(), nka_transitions[*iter_s][cur_symbol].end());
				}

				iter_s++;
			}

			if (!new_nodes_set.empty()) {

				if (dka_transitons.find(cur_nodes_set) == dka_transitons.end()) {

					dka_transitons.insert({ cur_nodes_set,  {new_nodes_set} });
				}
				else {
					dka_transitons[cur_nodes_set].push_back(new_nodes_set);
				}


				if (dka_nodes.find(new_nodes_set) == dka_nodes.end()) {
					dka_nodes.insert(new_nodes_set);
					usefull_dka_nodes.push_back(new_nodes_set);
					q_set.push(new_nodes_set);
				}
			}




			new_nodes_set.clear();
		}
	}


	set<set<ll>> dka_terminals;


	for (ll i = 1; i < usefull_dka_nodes.size(); i++)
	{
		set<ll>::iterator iter_v = usefull_dka_nodes[i].begin();

		for (ll j = 0; j < usefull_dka_nodes[i].size(); j++)
		{
			if (nka_terminals.find(*iter_v) != nka_terminals.end()) {
				dka_terminals.insert(usefull_dka_nodes[i]);
				break;
			}

			iter_v++;
		}
	}




	map<set<ll>, ll> set_to_num;

	for (ll i = 1; i < usefull_dka_nodes.size(); i++)
	{
		set_to_num.insert({ usefull_dka_nodes[i], i });
	}


	// part about matrix;

	ll m_length = usefull_dka_nodes.size() - 1;

	vector<vector<ll>> matrix;
	matrix.resize(m_length);

	vector<vector<ll>> dop_matrix;
	dop_matrix.resize(m_length);

	for (ll i = 0; i < m_length; i++)
	{
		matrix[i].resize(m_length);
		dop_matrix[i].resize(m_length);
	}

	for (ll i = 1; i <= m_length; i++)
	{
		if (dka_transitons.find(usefull_dka_nodes[i]) != dka_transitons.end()) {

			vector<set<ll>> local_v = dka_transitons[usefull_dka_nodes[i]];

			for (ll j = 0; j < local_v.size(); j++)
			{
				matrix[i - 1][set_to_num[local_v[j]] - 1] += 1;
			}
		}
	}


	matrix = fast_pow_matrix(matrix, dop_matrix, l);

	vector<ll> qs = matrix[0];

	set<set<ll>>::iterator iter_s = dka_terminals.begin();

	ll count = 0;

	for (ll i = 0; i < dka_terminals.size(); i++)
	{
		count += qs[set_to_num[*iter_s] - 1];
		iter_s++;
	}

	count = (count) % MOD;

	out << count;

	in.close();
	out.close();

	return 0;
}



vector<vector<ll>> square_matrix(vector<vector<ll>> v1, vector<vector<ll>> v2, vector<vector<ll>> dop) {

	ll n = v1.size();

	for (ll i = 0; i < n; i++)
	{
		for (ll j = 0; j < n; j++)
		{
			ll cur_sum = 0;

			for (ll k = 0; k < n; k++)
			{
				cur_sum = (cur_sum + v1[i][k] * v2[k][j]) % MOD;
			}

			dop[i][j] = cur_sum;
		}
	}

	return dop;
}


vector<vector<ll>> fast_pow_matrix(vector<vector<ll>> v, vector<vector<ll>> dop, ll n) {
	if (n == 1) {
		return v;
	}
	else if (n % 2 == 0) {
		return fast_pow_matrix(square_matrix(v, v, dop), dop, n / 2);
	}
	else if (n % 2 == 1) {
		return square_matrix(v, fast_pow_matrix(v, dop, n - 1), dop);
	}
}