#include <stdio.h>

int main() {

	int n;
	scanf_s("%d", &n);

	for (int i = 1; i <= n; i *= 2) {
		printf("%d\n", i);

	}
}