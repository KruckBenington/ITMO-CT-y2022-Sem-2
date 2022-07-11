//#include <iostream>
//#include <string>
//#include <fstream>
//#include <vector>
//#include <utility>
//#include <set>
//
//using namespace std;
//
//typedef long long ll;
//
//const ll MOD = 1000000000 + 7;
//
//
//vector<vector<ll>> square_matrix(vector<vector<ll>> v1, vector<vector<ll>> v2, vector<vector<ll>> dop);
//vector<vector<ll>> fast_pow_matrix(vector<vector<ll>> v, vector<vector<ll>> dop, ll n);
//
//int who(int argc, char* argv[]) {
//
//	ifstream in("problem4.in");
//	ofstream out("problem4.out");
//
//	ll n, m, k, l;
//
//
//	in >> n >> m >> k >> l;
//	vector<vector<ll>> v;
//	v.resize(n);
//
//	for (ll i = 0; i < n; i++)
//	{
//		v[i].resize(n);
//	}
//
//	vector<vector<ll>> dop;
//	dop.resize(n);
//
//	for (ll i = 0; i < n; i++)
//	{
//		dop[i].resize(n);
//	}
//
//
//	set<ll> terminals;
//
//	ll number, to_state;
//	char trash;
//
//	for (ll i = 0; i < k; i++)
//	{
//		in >> number;
//		terminals.insert(number);
//	}
//
//	for (ll i = 0; i < m; i++)
//	{
//		in >> number >> to_state >> trash;
//		v[number - 1][to_state - 1] += 1;
//	}
//
//	v = fast_pow_matrix(v, dop, l);
//
//	vector<ll> qs = v[0];
//
//	set<ll>::iterator iter_s = terminals.begin();
//
//	ll count = 0;
//
//	for (ll i = 0; i < terminals.size(); i++)
//	{
//		count += qs[*iter_s - 1];
//		iter_s++;
//	}
//
//	count = (count) % MOD;
//
//	out << count;
//
//	in.close();
//	out.close();
//
//	return 0;
//}
//
//
//vector<vector<ll>> square_matrix(vector<vector<ll>> v1, vector<vector<ll>> v2, vector<vector<ll>> dop) {
//	
//	ll n = v1.size();
//
//	for (ll i = 0; i < n; i++)
//	{
//		for (ll j = 0; j < n; j++)
//		{
//			ll cur_sum = 0;
//
//			for (ll k = 0; k < n; k++)
//			{
//				cur_sum = (cur_sum + v1[i][k] * v2[k][j]) % MOD;
//			}
//
//			dop[i][j] = cur_sum;
//		}
//	}
//
//	return dop;
//}
//
//
//vector<vector<ll>> fast_pow_matrix(vector<vector<ll>> v, vector<vector<ll>> dop, ll n) {
//	if (n == 1) {
//		return v;
//	}
//	else if (n % 2 == 0) {
//		return fast_pow_matrix(square_matrix(v, v, dop), dop, n / 2);
//	}
//	else if (n % 2 == 1) {
//		return square_matrix(v, fast_pow_matrix(v, dop, n - 1), dop);
//	}
//}
