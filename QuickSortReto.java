import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// 1. Clase para representar cada fila del CSV
class Persona {
    String nombre;
    String tipo;

    public Persona(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return nombre + " : " + tipo;
    }
}

public class QuickSortReto {

    public static void main(String[] args) {
        String archivoCSV = "listado.csv";
        List<Persona> lista = new ArrayList<>();

        // 2. Leer el archivo CSV
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                // Saltar la cabecera si existe
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] datos = linea.split(";");
                if (datos.length >= 2) {
                    // Limpiamos espacios extra por seguridad
                    lista.add(new Persona(datos[0].trim(), datos[1].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("--- Datos desordenados (primeros 5) ---");
        imprimirMuestra(lista);

        // 3. Ejecutar Quick Sort
        quickSort(lista, 0, lista.size() - 1);

        System.out.println("\n--- Datos ordenados (Mujeres -> Hombres -> Apellidos) ---");
        // Imprimimos toda la lista o una muestra
        for (Persona p : lista) {
            System.out.println(p);
        }
    }

    // --- Implementación del Algoritmo Quick Sort ---

    private static void quickSort(List<Persona> lista, int bajo, int alto) {
        if (bajo < alto) {
            // pi es el índice de partición
            int pi = partition(lista, bajo, alto);

            // Ordenamos recursivamente los elementos antes y después de la partición
            quickSort(lista, bajo, pi - 1);
            quickSort(lista, pi + 1, alto);
        }
    }

    private static int partition(List<Persona> lista, int bajo, int alto) {
        Persona pivote = lista.get(alto);
        int i = (bajo - 1); // Índice del elemento más pequeño

        for (int j = bajo; j < alto; j++) {
            // Si el elemento actual es "menor" que el pivote según nuestra lógica
            if (comparar(lista.get(j), pivote) < 0) {
                i++;
                // Intercambiar lista[i] y lista[j]
                Collections.swap(lista, i, j);
            }
        }

        // Intercambiar lista[i+1] y lista[alto] (o pivote)
        Collections.swap(lista, i + 1, alto);

        return i + 1;
    }

    // --- Lógica de Comparación Personalizada ---
    
    // Devuelve negativo si p1 va antes que p2
    // Devuelve positivo si p1 va después de p2
    // Devuelve 0 si son iguales
    private static int comparar(Persona p1, Persona p2) {
        // 1. Comparar por TIPO (Prioridad personalizada)
        int prioridad1 = obtenerPrioridad(p1.tipo);
        int prioridad2 = obtenerPrioridad(p2.tipo);

        if (prioridad1 != prioridad2) {
            return prioridad1 - prioridad2;
        }

        // 2. Si el tipo es el mismo, comparar por NOMBRE (Alfabético estándar)
        return p1.nombre.compareToIgnoreCase(p2.nombre);
    }

    // Asigna un valor numérico para forzar el orden específico
    private static int obtenerPrioridad(String tipo) {
        String tipoNormalizado = tipo.toLowerCase().trim();
        switch (tipoNormalizado) {
            case "mujer": return 1;    // Prioridad 1
            case "hombre": return 2;   // Prioridad 2
            case "apellido": return 3; // Prioridad 3
            default: return 4;         // Otros
        }
    }

    private static void imprimirMuestra(List<Persona> lista) {
        for (int i = 0; i < Math.min(5, lista.size()); i++) {
            System.out.println(lista.get(i));
        }
    }
}


