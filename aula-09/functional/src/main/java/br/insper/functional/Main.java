package br.insper.functional;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String  [] args) {
            // Criar uma lista de strings
            List<String> frases = Arrays.asList(
                    "O rato roeu a roupa do rei de Roma",
                    "A rainha de copas quer cortar a cabeça do rei de espadas",
                    "O lobo mau assoprou e derrubou a casa dos porquinhos",
                    "O rei de copas e a rainha de espadas discutiram durante o chá"
            );

            // Exemplo 1 - Contar frases que começam com A
            Integer frasesComecamA = 0;
            for (String f : frases) {
                if (f.startsWith("A")) {
                    frasesComecamA++;
                }
            }

            System.out.println(frasesComecamA);

            // Exemplo 2 - Filtrar apenas as frases que contenham a palavra rainha
            List<String> frasesRainha = new ArrayList<>();
            for (String frase : frases) {
                if (frase.contains("rainha")) {
                    frasesRainha.add(frase);
                }
            }
            System.out.println(frasesRainha);

            // Exemplo 3 - Converter a lista de frases em uma lista com a quantidade de caracteres de cada frase
            List<Integer> tamanho = new ArrayList<>();
            for (String frase : frases) {
                tamanho.add(frase.length());
            }
            System.out.println(tamanho);

            tamanho = frases
                    .stream()
                    .map(x -> x.length())
                    .toList();
        System.out.println(tamanho);

            // Exemplo 3 - Frase com maior número de palavras
            Integer fraseMaiorNumeroPalavras = 0;
            for (String f : frases) {
                Integer numero = f.length();
                if (numero > fraseMaiorNumeroPalavras) {
                    fraseMaiorNumeroPalavras = numero;
                }
            }
            System.out.println(fraseMaiorNumeroPalavras);

            // Exemplo 4 - Contar total de digitos na frase
            Integer totalDigitos = 0;
            for (String f : frases) {
                totalDigitos += f.length();
            }

            System.out.println(totalDigitos);

            // Exemplo 5 - Quantas vezes a palavra rainha
            Integer rainha = 0;
            for (String f : frases) {
                String [] palavras = f.split(" ");
                for (String palavra : palavras) {
                    if (palavra.equalsIgnoreCase("rainha")) {
                        rainha++;
                    }
                }
            }
            System.out.println(rainha);

            // Exemplo 6 - Word Count
            Map<String, Integer> wordCount = new HashMap<>();

            for (String frase : frases) {
                String[] palavras = frase.split("\\s+");

                for (String palavra : palavras) {
                    palavra = palavra.toLowerCase();
                    if (wordCount.containsKey(palavra)) {
                        wordCount.put(palavra, wordCount.get(palavra) + 1);
                    } else {
                        wordCount.put(palavra, 1);
                    }
                }
            }

            System.out.println(wordCount);






    }
}
