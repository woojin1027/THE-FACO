 #include <stdio.h>

int main() {
	printf("hello world \n 이걸로 이런 연습이나 하다니,,,\n");
	printf(" 연습해보라고\n 하길래\n");
	// %d : 정수를 넣는것
	printf("%d +  %d = %d\n", 2, 3, 5);
	// %f : 실수를 넣는것
	printf("%f\n", 3.141235);
	printf("%.2f\n", 3.141235);
	//%g : 실수 넣는것 단, 지수형태로도 출력 가능
	printf("%2g\n", 4366546.14122342135);
	//%c : 문자 출력 단, 한글은 불가능
	printf("%c,%c,%c\n", 'a', 'b', 'c');
	// %s : 문자열 출력 단, 한글 가능
	printf("%s\n", "안녕이닷");

	int a;

	a = 3;
	printf("%d\n", a);

	a = 5;
	printf("%d\n", a);

}