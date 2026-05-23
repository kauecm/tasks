package com.automatizacao.tasks.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.automatizacao.tasks.dtos.TarefaDTO;

@Service
public class LeituraXlsxService {

	public List<TarefaDTO> ler(String caminhoArquivo) {

        List<TarefaDTO> tarefas = new ArrayList<>();

        try (
                FileInputStream fis = new FileInputStream(caminhoArquivo);
                XSSFWorkbook workbook = new XSSFWorkbook(fis)
        ) {
        	
        	int numeroDeAbas = workbook.getNumberOfSheets();
        	DataFormatter formatter = new DataFormatter();
        	
        	for (int a = 0; a < numeroDeAbas; a++) {
        		XSSFSheet sheet = workbook.getSheetAt(a);
        		
        		// começa na linha 1 ignorando cabeçalho
        		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
        			
        			Row row = sheet.getRow(i);
        			
        			if (row == null) {
        				continue;
        			}
        			
        			// ignora linhas totalmente vazias
        			if (linhaVazia(row)) {
        				continue;
        			}
        			
        			TarefaDTO dto = new TarefaDTO(
        					get(row, 0, formatter),
        					get(row, 1, formatter),
        					get(row, 2, formatter),
        					get(row, 3, formatter),
        					get(row, 4, formatter),
        					get(row, 5, formatter),
        					get(row, 6, formatter),
        					get(row, 7, formatter),
        					get(row, 8, formatter),
        					get(row, 9, formatter),
        					get(row, 10, formatter),
        					get(row, 11, formatter),
        					get(row, 12, formatter)
        					);
        			
        			tarefas.add(dto);
        		}
        	}




        } catch (IOException e) {
            e.printStackTrace();
        }

        return tarefas;
    }

    private String get(Row row, int index, DataFormatter formatter) {

        Cell cell = row.getCell(index);

        if (cell == null) {
            return "";
        }

        return formatter.formatCellValue(cell);
    }

    private boolean linhaVazia(Row row) {

        for (Cell cell : row) {

            if (cell != null && !cell.toString().trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}