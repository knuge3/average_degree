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
public class Edge {
    public String data;
    private Date ttl;
    
    public Edge(){
        data = null;
        ttl = null;
    }
    public Edge(String s, Date i){
        data = s;
        ttl = i;
    }
    public Date get_time(){
        return ttl;
    }
    public void set_Time(Date t){
        ttl = t;
    }
}
