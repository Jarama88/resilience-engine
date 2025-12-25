import core.Executor;
import core.ResilienceException;
import tasks.NetworkTask;
import strategies.CircuitBreakerStrategy;
import monitoring.ErrorLogger;

public class Main {
    public static void main(String[] args) {
        NetworkTask task = new NetworkTask();
        
        // Disjuntor: abre após 3 falhas, espera 5 segundos para tentar de novo
        Executor executor = new Executor(new CircuitBreakerStrategy(3, 5000));
        executor.addListener(new ErrorLogger());

        System.out.println("--- Iniciando Teste de Stress do Circuit Breaker ---");

        for (int i = 1; i <= 6; i++) {
            try {
                System.out.println("\nExecucao #" + i);
                String result = executor.execute(task);
                System.out.println("Resultado: " + result);
            } catch (ResilienceException e) {
                System.err.println("Capturado na Main: " + e.getMessage());
            }
            
            // Pequena pausa para nao poluir o console instantaneamente
            try { Thread.sleep(500); } catch (InterruptedException ie) {}
        }
    }
}