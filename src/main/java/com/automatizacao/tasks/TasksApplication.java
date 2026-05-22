package com.automatizacao.tasks;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.automatizacao.tasks.dtos.TarefaDTO;
import com.automatizacao.tasks.service.LeituraXlsxService;
import com.automatizacao.tasks.service.ServiceTasks;

@SpringBootApplication
public class TasksApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(TasksApplication.class, args);
		 var context = new SpringApplicationBuilder(TasksApplication.class)
	                .web(WebApplicationType.NONE)
	                .run(args);
		 ServiceTasks serviceTasks = context.getBean(ServiceTasks.class);
		 
		 LeituraXlsxService leituraXlsxService = context.getBean(LeituraXlsxService.class);

	        //serviceTasks.autenticar();

	        String caminhoArquivo = "/home/kaue.moraes/Downloads/modelo-input-tasks.xlsx";

	        List<TarefaDTO> tarefas = leituraXlsxService.ler(caminhoArquivo);

	        for (TarefaDTO tarefa : tarefas) {
	        	System.out.println(tarefa.toString());
	        	Thread.sleep(60000L);
	        	//serviceTasks.cadastrar(tarefa);

	        }
	}

}
