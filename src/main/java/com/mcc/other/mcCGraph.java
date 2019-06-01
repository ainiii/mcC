package other;

import ast.RootNode;
import ast.AstNode;
import ast.ValueNode;
import com.mxgraph.layout.mxCompactTreeLayout;
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

        mxCompactTreeLayout layout = new mxCompactTreeLayout(jgxAdapter, false);
        layout.setEdgeRouting(false);

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
        Vertex root = new Vertex("Root", this.id++);
        this.directedGraph.addVertex(root);

        // preorder (root, left, right)
        for (AstNode node : this.tree.nodes) {
            this.preorder(node, null, root);
        }
    }

    private void preorder(AstNode node, @Nullable Vertex parent, Vertex root) {
        if (node == null) {
            return;
        }

        Vertex temp;

        if (node instanceof ValueNode) {
            temp = new Vertex(((ValueNode)node).value, this.id++);
        } else {
            temp = new Vertex(node.getClass().getSimpleName(), this.id++);
        }

        this.directedGraph.addVertex(temp);

        if (parent != null) {
            this.directedGraph.addEdge(parent, temp, new Edge());
        } else {
            this.directedGraph.addEdge(root, temp, new Edge());
        }

        this.preorder(node.left, temp, null);
        this.preorder(node.right, temp, null);
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
