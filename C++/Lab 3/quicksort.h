template< typename T, bool descending >
void quicksort(T *arr, int left, int right)
{
	while (right - left > 0)
	{
		T m_el = arr[left];
		int local_l = left;
		int local_r = right;

		while (local_l <= local_r)
		{
			while ((local_l <= local_r) && ((arr[local_l] < m_el && !descending) || (arr[local_l] > m_el && descending)))
			{
				local_l++;
			}

			while ((local_l <= local_r) && ((arr[local_r] > m_el && !descending) || (arr[local_r] < m_el && descending)))
			{
				local_r--;
			}

			if (local_l >= local_r)
			{
				break;
			}

			T c = arr[local_l];
			arr[local_l] = arr[local_r];
			arr[local_r] = c;
			local_l++;
			local_r--;
		}

		if (right - (local_r + 1) > local_r - left)
		{
			quicksort< T, descending >(arr, left, local_r);

			left = local_r + 1;
		}
		else
		{
			quicksort< T, descending >(arr, local_r + 1, right);

			right = local_r;
		}
	}
}
