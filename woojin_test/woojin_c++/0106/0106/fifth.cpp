#include <stdio.h>

int main() {

	// break 문 이용
	for (int i = 1;; i++) {
		int k;
		scanf_s("%d", &k);
		if (k == 0) {
			break;
		}
		printf("%d 번째 : %d", i, k);
	}
}