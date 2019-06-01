import ast.RootNode;
import ast.AstNode;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jetbrains.annotations.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;

import javax.swing.*;
import java.awt.*;

public class mcCGraph extends JApplet {
    private RootNode tree;
    private Graph<Vertex, Edge> directedGraph;
    private JFrame frame;
    private int id = 0;
    private Dimension DEFAULT_SIZE = new Dimension(640, 480);

    public mcCGraph(RootNode tree) {
        this.tree = tree;
        this.init();
    }

    @Override
    public void init() {
        this.directedGraph = this.getEmptyGraph();
        this.fillGraph();

        JGraphXAdapter jgxAdapter = new JGraphXAdapter<>(this.directedGraph);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);

        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        layout.setX0((DEFAULT_SIZE.width / 2.0) - 100);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - 100);
        layout.setRadius(100);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
    }

    public void draw() {
        if (this.frame == null) {
            this.frame = new JFrame();
        }

        this.frame.getContentPane().add(this);
        this.frame.setTitle("mcC - AST");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void fillGraph() {
        // preorder (root, left, right)
        for (AstNode node : this.tree.nodes) {
            this.preorder(node, null);
        }
    }

    private void preorder(AstNode node, @Nullable Vertex parent) {
        if (node == null) {
            return;
        }

        Vertex temp = new Vertex(node.getClass().getSimpleName(), this.id++);
        this.directedGraph.addVertex(temp);

        if (parent != null) {
            this.directedGraph.addEdge(parent, temp, new Edge());
        }

        this.preorder(node.left, temp);
        this.preorder(node.right, temp);
    }

    private Graph<Vertex, Edge> getEmptyGraph() {
        return new DefaultDirectedGraph<>(Edge.class);
    }

    private class Vertex {
        private String field;
        private int id;

        public Vertex(String field, int id) {
            this.field = field;
            this.id = id;
        }

        @Override
        public String toString() {
            return field;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Vertex)) {
                return false;
            }

            Vertex vertex = (Vertex) obj;

            return this.field.equals(vertex.field) && this.id == vertex.id;
        }
    }

    private class Edge {
        public Edge() { }

        @Override
        public String toString() {
            return "";
        }
    }
}
