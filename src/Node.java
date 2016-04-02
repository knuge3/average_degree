/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashtag_graph;
import java.util.*;
/**
 *
 * @author kylen
 */
public class Node {
    public String name;
    private Date ttl;
    public List<Edge> edges;
    private int num_edges;
    
    public Node(){
        name = null;
        ttl = null;
        edges = new ArrayList<Edge>();
        num_edges = 0;
    }
    public Node(String s, Date i){
        name = s;
        ttl = i;
        edges = new ArrayList<Edge>();
        num_edges = 0;
    }
    /**
     * Will check if an edge is already in the node list
     * If it is, it will also check if the new edge data has a more
     * recent timestamp. If it does, it will update the older timestamp
     */
    public void add_edge(Edge e){
       for(int i = 0; i < edges.size(); i++){
             if(edges.get(i).data.equals(e.data)){
                 if(edges.get(i).get_time().before(e.get_time())){
                     edges.get(i).set_Time(e.get_time());
                     break;
                 }
                 else{
                     break;
                 }
             }
             else{
                edges.add(e);
                num_edges += 1;
        }
       }        
    }
    public void remove_edge(Edge e){
        if(edges.contains(e)){
            edges.remove(e);
            num_edges -= 1;
        }
    }
    public void set_ttl(Date t){
        ttl = t;
    }
    public Date get_ttl(){
        return ttl;
    }
    public int get_Num_Edges(){
        return num_edges;
    }
    public boolean empty(){
        return edges.isEmpty();
    }
}
