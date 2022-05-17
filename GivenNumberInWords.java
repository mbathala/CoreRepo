package com.myex.core;

public class GivenNumberInWords {

	/*
	 * The first string is not used, it is to make array indexing simple
	 */
	static String[] single_digits = new String[] { "zero", "one", "two", "three", "four", "five", "six", "seven",
			"eight", "nine" };

	/*
	 * The first string is not used, it is to make array indexing simple
	 */
	static String[] two_digits = new String[] { "", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
			"sixteen", "seventeen", "eighteen", "nineteen" };

	/* The first two string are not used, they are to make array indexing simple */
	static String[] tens_multiple = new String[] { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy",
			"eighty", "ninety" };

	static String[] tens_power = new String[] { "hundred", "thousand", "million" };

	public static void convert_to_words(String number) {

		if (number != null && number.length() > 0) {
			// Removing commas from number
			if (number.contains(",")) {
				String[] numbersWitComas = number.split(",");
				number = "";
				for (String num : numbersWitComas)
					number += num;
			}

			if (!isNumeric(number) || number.length() > 9) {
				System.out.println("Only numbers up to 999,999,999 will be supported");
				return;
			}
			
			char[] num = number.toCharArray();
			int len = num.length;
			
			/* Used for debugging purpose only */
			System.out.print(String.valueOf(num) + ": ");

			/* For single digit number */
			if (len == 1) {
				System.out.println(single_digits[num[0] - '0']);
				return;
			}

			/*
			 * Iterate while num is not '\0'
			 */
			int x = 0;
			while (x < num.length) {

				if (len >= 3) {
					if ((len % 3 == 0 || len == 4 || len == 7) && num[x] - '0' != 0) {
						System.out.print(single_digits[num[x] - '0'] + " ");

						if (len % 3 == 0) {
							System.out.print(tens_power[len % 3] + " ");
							System.out.print("and ");
						} else {
							System.out.print(tens_power[len / 3] + " ");
						}
						// here len can be 3 or 4
					} else if (len > 4) {
						x = handleTwoDigits(num, x);
						System.out.print(tens_power[len / 3] + " ");
                                                System.out.print("");
						if (len == 5) {
							--len;
						} else if (len == 8) {
							--len;
						}
					}
					--len;
				}

				/* Code path for last 2 digits */
				else if (len >= 2) {
					x = handleTwoDigits(num, x);
				}
				++x;
			}
			System.out.println("");
		}
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static int handleTwoDigits(char[] num, int x) {
		/*
		 * Need to explicitly handle 10-19. Sum of the two digits is used as index of
		 * "two_digits" array of strings
		 */
		if (num[x] - '0' == 1) {
			int sum = num[x] - '0' + num[++x] - '0';
			System.out.print(two_digits[sum] + " ");
			return x;
		}

		/*
		 * Rest of the two digit numbers i.e., 21 to 99
		 */
		else {
			int i = (num[x] - '0');
			if (i > 0)
				System.out.print(tens_multiple[i] + " ");
			else
				System.out.print("");
			++x;
			if (num[x] - '0' != 0)
				System.out.print(single_digits[num[x] - '0'] + " ");
			else
				System.out.println("");
		}
		return x;
	}

	public static void main(String[] args) {
		convert_to_words("6119891");
		convert_to_words("999,999,999");
		convert_to_words("asdfasdf");
	}

}
