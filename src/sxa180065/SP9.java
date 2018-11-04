/** Sample starter code for SP9.
 *  @author
 */

package sxa180065;

import java.util.Random;

public class SP9 {
	public static Random random = new Random();
	public static int numTrials = 1;

	public static void main(String[] args) {
		int n = 80000000;
		int choice = 1 + random.nextInt(4);
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		if (args.length > 1) {
			choice = Integer.parseInt(args[1]);
		}
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = i;
		}
		Timer timer = new Timer();
		switch (choice) {
		case 1:
			Shuffle.shuffle(arr);
			numTrials = 1;
			insertionSort(arr);
			break;
		case 2:
			for (int i = 0; i < numTrials; i++) {
				Shuffle.shuffle(arr);
				mergeSort1(arr);
			}
			break; // etc
		case 3:
			for (int i = 0; i < numTrials; i++) {
				Shuffle.shuffle(arr);
				mergeSort2(arr);
			}
			break;
		case 4:
			for (int i = 0; i < numTrials; i++) {
				Shuffle.shuffle(arr);
				mergeSort3(arr);
			}
			break;
		}
		timer.end();
		timer.scale(numTrials);

		System.out.println("Choice: " + choice + "\n" + timer);
	}

	public static void insertionSort(int[] arr, int p, int r) {
		for (int i = p + 1; i <= r; i++) {
			int temp = arr[i];
			int j = i - 1;
			while (j >= p && temp < arr[j]) {
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = temp;
		}
	}

	public static void insertionSort(int[] arr) {
		insertionSort(arr, 0, arr.length - 1);

	}

	public static void merge1(int[] arr, int p, int q, int r) {
		int m = q - p + 1;
		int n = r - q;
		int L[] = new int[m];
		int R[] = new int[n];
		System.arraycopy(arr, p, L, 0, m);
		System.arraycopy(arr, q + 1, R, 0, n);
		int i = 0, j = 0;
		for (int k = p; k <= r; k++) {
			if (j >= n || (i < m && L[i] <= R[j])) {
				arr[k] = L[i++];
			} else {
				arr[k] = L[j++];
			}
		}
	}

	public static void mergeSort1(int[] arr, int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;
			mergeSort1(arr, p, q);
			mergeSort1(arr, q + 1, r);
			merge1(arr, p, q, r);
		}
	}

	public static void mergeSort1(int[] arr) {
		mergeSort1(arr, 0, arr.length - 1);
	}

	public static void mergeSort2(int[] arr) {
		mergeSort2(arr, 0, arr.length - 1);
		/*
		 * for(int i=0; i<arr.length; i++) System.out.print(arr[i] + "\t");
		 * System.out.println();
		 */
	}

	public static void mergeSort2(int[] A, int p, int r) {
		if (r - p + 1 < 7)
			insertionSort(A, p, r);
		else {
			int q = (r + p) / 2;
			mergeSort2(A, p, q);
			mergeSort2(A, q + 1, r);
			int[] tempArr = new int[A.length];
			merge2(A, tempArr, p, q, r);
		}
	}

	public static void merge2(int[] A, int[] B, int p, int q, int r) {
		System.arraycopy(A, p, B, p, r - p + 1);
		int i = p, j = q + 1, k = p;
		while (k <= r) {
			if (j > r || (i <= q && B[i] <= B[j]))
				A[k++] = B[i++];
			else if (j <= r)
				A[k++] = B[j++];
		}
	}

	public static void mergeSort3(int[] arr) {
		int arrLength = arr.length;
		int[] tempArr = new int[arrLength];
		System.arraycopy(arr, 0, tempArr, 0, arrLength);
		mergeSort3(arr, tempArr, 0, arrLength);
	}

	public static void mergeSort3(int[] A, int[] B, int left, int n) {
		if (n < 4)
			insertionSort(A, left, left + n - 1);
		else {
			int Ln = n / 2;
			mergeSort3(B, A, left, Ln);
			mergeSort3(B, A, left + Ln, n - Ln);
			merge3(A, B, left, left + Ln - 1, left + n - 1);
		}
	}

	public static void merge3(int[] A, int B[], int p, int q, int r) {
		int i = p, j = q + 1, k = p;
		while (i <= q && j <= r) {
			if (B[i] <= B[j])
				A[k++] = B[i++];
			else
				A[k++] = B[j++];
		}
		while (i <= q)
			A[k++] = B[i++];
		while (j <= r)
			A[k++] = B[j++];
	}

	/**
	 * Timer class for roughly calculating running time of programs
	 * 
	 * @author rbk Usage: Timer timer = new Timer(); timer.start(); timer.end();
	 *         System.out.println(timer); // output statistics
	 */

	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() {
			if (!ready) {
				end();
			}
			return elapsedTime;
		}

		public long memory() {
			if (!ready) {
				end();
			}
			return memUsed;
		}

		public void scale(int num) {
			elapsedTime /= num;
		}

		public String toString() {
			if (!ready) {
				end();
			}
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed / 1048576) + " MB / "
			    + (memAvailable / 1048576) + " MB.";
		}
	}

	/**
	 * @author rbk : based on algorithm described in a book
	 */

	/* Shuffle the elements of an array arr[from..to] randomly */
	public static class Shuffle {

		public static void shuffle(int[] arr) {
			shuffle(arr, 0, arr.length - 1);
		}

		public static <T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length - 1);
		}

		public static void shuffle(int[] arr, int from, int to) {
			int n = to - from + 1;
			for (int i = 1; i < n; i++) {
				int j = random.nextInt(i);
				swap(arr, i + from, j + from);
			}
		}

		public static <T> void shuffle(T[] arr, int from, int to) {
			int n = to - from + 1;
			Random random = new Random();
			for (int i = 1; i < n; i++) {
				int j = random.nextInt(i);
				swap(arr, i + from, j + from);
			}
		}

		static void swap(int[] arr, int x, int y) {
			int tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		static <T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		public static <T> void printArray(T[] arr, String message) {
			printArray(arr, 0, arr.length - 1, message);
		}

		public static <T> void printArray(T[] arr, int from, int to, String message) {
			System.out.print(message);
			for (int i = from; i <= to; i++) {
				System.out.print(" " + arr[i]);
			}
			System.out.println();
		}
	}
}
