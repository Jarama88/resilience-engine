package tasks;

import core.Task;
import core.ResilienceException;
import java.util.Random;

public class NetworkTask implements Task<String> {
    @Override
    public String execute() throws ResilienceException {
        if (new Random().nextInt(10) < 7) {
            throw new ResilienceException("Timeout de conexao");
        }
        return "{ 'status': '200', 'data': 'UFSCar_Data' }";
    }

    @Override
    public String getName() {
        return "Chamada_API_Externa";
    }
}