/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.yahoostorm;

import java.util.*;
import java.io.*;
import java.math.BigDecimal;

//import yahoofinace packages
import yahoofinance.YahooFinance;
import yahoofinance.Stock;

import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;

public class YahooFinanceSpout implements IRichSpout {
   private SpoutOutputCollector collector;
   private boolean completed = false;
   private TopologyContext context;
	
   @Override
   public void open(Map conf, TopologyContext context, SpoutOutputCollector collector){
      this.context = context;
      this.collector = collector;
   }

   @Override
   public void nextTuple() {
      try {
         Stock stock = YahooFinance.get("INTC");
         BigDecimal price = stock.getQuote().getPrice();

         this.collector.emit(new Values("INTC", price.doubleValue()));
         stock = YahooFinance.get("GOOGL");
         price = stock.getQuote().getPrice();

         this.collector.emit(new Values("GOOGL", price.doubleValue()));
         stock = YahooFinance.get("AAPL");
         price = stock.getQuote().getPrice();

         this.collector.emit(new Values("AAPL", price.doubleValue()));
      } catch(Exception e) {}
   }

   @Override
   public void declareOutputFields(OutputFieldsDeclarer declarer) {
      declarer.declare(new Fields("company", "price"));
   }

   @Override
   public void close() {}
	
   public boolean isDistributed() {
      return false;
   }

   @Override
   public void activate() {}

   @Override
   public void deactivate() {}

   @Override
   public void ack(Object msgId) {}

   @Override
   public void fail(Object msgId) {}

   @Override
   public Map<String, Object> getComponentConfiguration() {
      return null;
   }
	
}