#include <stdio.h>
#include <string.h>

int main() {

	char str[100] = "hello";
	int len;
	len = strlen(str);
	printf(" ���ڿ��� ���̴� : %d\n", len);

	strcat_s(str, " world");
	printf(" �� ���� ���ڴ� %s", str);
}