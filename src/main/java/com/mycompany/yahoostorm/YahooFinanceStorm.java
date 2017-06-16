/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.yahoostorm;

/**
 *
 * @author estudiante
 */
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;

public class YahooFinanceStorm {
   public static void main(String[] args) throws Exception{
      Config config = new Config();
      config.setDebug(true);
		
      TopologyBuilder builder = new TopologyBuilder();
      builder.setSpout("yahoo-finance-spout", new YahooFinanceSpout());

      builder.setBolt("price-cutoff-bolt", new PriceCutOffBolt())
         .fieldsGrouping("yahoo-finance-spout", new Fields("company"));
			
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("YahooFinanceStorm", config, builder.createTopology());
      Thread.sleep(10000);
      cluster.shutdown();
   }
}
