import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Node {
    Point point;
    Node parent;
    int cost;
    int heuristic;
    int totalCost;
    boolean isObstacle;
    boolean isStart;
    boolean isGoal;
    boolean isVisited;
    boolean isFrontier;

    Node(Point point, int heuristic, boolean isObstacle) {
        this.point = point;
        this.heuristic = heuristic;
        this.isObstacle = isObstacle;
    }
}

class VisitedMap {
    private final Map<Point, Boolean> data = new HashMap<>();

    public void visit(Point p) {
        data.put(p, true);
    }

    public boolean isVisited(Point p) {
        return data.getOrDefault(p, false);
    }
}

public class Main {
    public static void main(String[] args) {
        String labyrinthFile = "C:\\Users\\david\\OneDrive\\Escritorio\\6to semestre\\estructuras de datos clases\\laberintoRecursividad\\laberinto.txt";

        try {
            List<String> lines = Files.readAllLines(Paths.get(labyrinthFile));
            List<char[][]> labyrinths = parseLabyrinths(lines);

            int labyrinthCount = 1;
            for (char[][] labyrinth : labyrinths) {
                System.out.println("Resolviendo laberinto " + labyrinthCount + ":");
                Point start = findStart(labyrinth);
                Point end = findEnd(labyrinth);

                if (start == null || end == null) {
                    System.out.println("No se encontraron las coordenadas de inicio (A) y/o fin (B) en el laberinto");
                } else {
                    List<List<Node>> paths = findPaths(labyrinth, start, end);
                    if (paths.isEmpty()) {
                        System.out.println("No se encontró un camino desde A hasta B.");
                    } else {
                        System.out.println("El laberinto tiene solución.");
                        System.out.println("Número de movimientos totales: " + (paths.get(0).size() - 1));
                    }

                    // Crear un conjunto de puntos que marcan todos los caminos
                    Set<Point> pathPoints = new HashSet<>();
                    for (List<Node> path : paths) {
                        for (Node node : path) {
                            pathPoints.add(node.point);
                        }
                    }

                    // Imprimir el laberinto con todos los caminos posibles
                    for (int i = 0; i < labyrinth.length; i++) {
                        for (int j = 0; j < labyrinth[i].length; j++) {
                            Point p = new Point(i, j);
                            boolean isPath = pathPoints.contains(p);
                            if (labyrinth[i][j] == '*') {
                                System.out.print('*');
                            } else if (labyrinth[i][j] == 'A') {
                                System.out.print('A');
                            } else if (labyrinth[i][j] == 'B') {
                                System.out.print('B');
                            } else {
                                System.out.print(isPath ? '.' : ' ');
                            }
                        }
                        System.out.println();
                    }
                }
                System.out.println();
                labyrinthCount++;
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static List<char[][]> parseLabyrinths(List<String> lines) {
        List<char[][]>  labyrinths = new ArrayList<>();
        List<String> currentLabyrinthLines = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                if (!currentLabyrinthLines.isEmpty()) {
                    char[][] labyrinth = currentLabyrinthLines.stream().map(String::toCharArray).toArray(char[][]::new);
                    labyrinths.add(labyrinth);
                    currentLabyrinthLines.clear();
                }
            } else {
                currentLabyrinthLines.add(line);
            }
        }

        if (!currentLabyrinthLines.isEmpty()) {
            char[][] labyrinth = currentLabyrinthLines.stream().map(String::toCharArray).toArray(char[][]::new);
            labyrinths.add(labyrinth);
        }

        return labyrinths;
    }

    private static Point findStart(char[][] labyrinth) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                if (labyrinth[i][j] == 'A') {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private static Point findEnd(char[][] labyrinth) {
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                if (labyrinth[i][j] == 'B') {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private static List<List<Node>> findPaths(char[][] labyrinth, Point start, Point end) {
        Map<Point, Node> graph = new HashMap<>();
        PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.totalCost));
        VisitedMap visited = new VisitedMap();

        Node startNode = new Node(start, calculateHeuristic(start, end), false);
        startNode.isStart = true;
        startNode.totalCost = startNode.heuristic;
        graph.put(start, startNode);
        frontier.add(startNode);

        Node endNode = new Node(end, 0, false);
        graph.put(end, endNode);

        List<List<Node>> allPaths = new ArrayList<>();
        int shortestPathLength = Integer.MAX_VALUE;

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();

            if (currentNode.point.x == end.x && currentNode.point.y == end.y) {
                List<Node> path = reconstructPath(currentNode);
                if (path.size() <= shortestPathLength) {
                    if (path.size() < shortestPathLength) {
                        allPaths.clear();
                        shortestPathLength = path.size();
                    }
                    allPaths.add(path);
                }
                continue;
            }

            currentNode.isVisited = true;

            for (Node neighbor : getNeighbors(graph, currentNode.point, visited, labyrinth, end)) {
                if (!neighbor.isVisited && !neighbor.isObstacle) {
                    int newCost = currentNode.cost + 1;

                    if (newCost < neighbor.cost || !neighbor.isFrontier) {
                        neighbor.cost = newCost;
                        neighbor.totalCost = newCost + neighbor.heuristic;
                        neighbor.parent = currentNode;

                        if (!neighbor.isFrontier) {
                            neighbor.isFrontier = true;
                            frontier.add(neighbor);
                        }
                    }
                }
            }
        }
        return allPaths;
    }

    private static List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node);
            node = node.parent;
        }
        return path;
    }

    private static List<Node> getNeighbors(Map<Point, Node> graph, Point p, VisitedMap visited, char[][] labyrinth, Point end) {
        List<Point> directions = Arrays.asList(
                new Point(-1, 0), // Arriba
                new Point(1, 0),  // Abajo
                new Point(0, -1), // Izquierda
                new Point(0, 1)   // Derecha
        );

        List<Node> neighbors = new ArrayList<>();

        for (Point dir : directions) {
            int newX = p.x + dir.x;
            int newY = p.y + dir.y;

            if (newX >= 0 && newX < labyrinth.length && newY >= 0 && newY < labyrinth[newX].length) {
                Point next = new Point(newX, newY);

                if (!visited.isVisited(next) && labyrinth[newX][newY] != '*') {
                    visited.visit(next);

                    Node node = graph.get(next);
                    if (node == null) {
                        node = new Node(next, calculateHeuristic(next, end), false);
                        graph.put(next, node);
                    }

                    neighbors.add(node);
                }
            }
        }

        return neighbors;
    }

    private static int calculateHeuristic(Point start, Point end) {
        return Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
    }
}
