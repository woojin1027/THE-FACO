#include <stdio.h>

int main() {

	// break �� �̿�
	for (int i = 1;; i++) {
		int k;
		scanf_s("%d", &k);
		if (k == 0) {
			break;
		}
		printf("%d ��° : %d", i, k);
	}
}