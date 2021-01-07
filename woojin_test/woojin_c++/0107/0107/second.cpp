#include <stdio.h>
#include <string.h>

int main() {

	char str[100] = "hello";
	int len;
	len = strlen(str);
	printf(" 문자열의 길이는 : %d\n", len);

	strcat_s(str, " world");
	printf(" 더 해진 글자는 %s", str);
}