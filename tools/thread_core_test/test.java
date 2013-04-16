import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

class Test {

	public static class TestTask implements Callable<Integer> {
		
		public Integer call() {
			double d = 0.0;
			for(int i=0; i<100000000; ++i) {
				d += Math.sin((double)i);
			}

			return (int)d;
		}
	}

	public static void main(String[] args) throws FileNotFoundException, InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newFixedThreadPool(12);
		
		List<Future<Integer>> futures = new ArrayList<Future<Integer>>();

		for (int i=0; i<12; ++i) {
			TestTask task = new TestTask();
			futures.add(exec.submit(task));
		}
		
		for (int i=0; i<12; ++i) {
			Integer c = futures.get(i).get();
			System.out.println(i + " " + c);
		}
		
		exec.shutdown();
	}
}

