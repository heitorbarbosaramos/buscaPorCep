package buscaPorCep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class BuscaPorCep implements Serializable{
	private static final long serialVersionUID = 1L;

    public static void buscarCep(String cepBuscar) {
        
    	
    	try {
			URL urlJson = new URL("http://viacep.com.br/ws/"+ cepBuscar +"/json");
			URLConnection connection = urlJson.openConnection();
	
            int response = ((HttpURLConnection) connection).getResponseCode();
            System.out.println("Código HTTP retorno: "+response);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
    	
            String inputLine;
            StringBuffer buffer = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
               buffer.append(inputLine);
            }
            
            in.close();
            
            final Gson gson = new GsonBuilder().create();
            
            JsonObject  json = gson.fromJson(buffer.toString(), JsonObject.class);
            
           
            String cep = (json.get("cep") != null) ? json.get("cep").toString().replace("\"", "") : null;
            String logradouro = (json.get("logradouro") != null) ? json.get("logradouro").toString().replace("\"", "") : null;
            String complemento = (json.get("complemento") != null) ? json.get("complemento").toString().replace("\"", "") : null;
            String bairro = (json.get("bairro") != null) ? json.get("bairro").toString().replace("\"", "") : null;
            String localidade = (json.get("localidade") != null) ? json.get("localidade").toString().replace("\"", "") : null;
            String uf = (json.get("uf") != null) ? json.get("uf").toString().replace("\"", "") : null;
            String unidade = (json.get("unidade") != null) ? json.get("unidade").toString().replace("\"", "") : null;
            String ibge = (json.get("ibge") != null) ? json.get("ibge").toString().replace("\"", "") : null;
            String gia = (json.get("gia") != null) ? json.get("gia").toString() : null; // Guia de Informação e Apuração do ICMS é uma declaração mensal, exigida na forma da legislação, cujas informações devem refletir a escrituração efetuada no Livro Fiscal Registro de Apuração do ICMS
            
            Endereco endereco = new Endereco(cep, logradouro, complemento, bairro, localidade, uf, unidade, ibge, gia);
            
            System.out.println(endereco);
          
            
    	} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    public static void main(String[] args) {
    	buscarCep("50010-140");
    }
}
