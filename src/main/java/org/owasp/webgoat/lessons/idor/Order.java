/*
 * SPDX-FileCopyrightText: Copyright © 2026 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.idor;

import java.util.HashMap;
import java.util.Map;

public class Order {
  private String orderId;
  private String userId;
  private String item;
  private String shippingAddress;
  private double total;

  public Order() {}

  public Order(String id) {
    setOrderFromId(id);
  }

  private void setOrderFromId(String id) {
    // emulate look up from database
    if (id.equals("9001")) {
      this.orderId = id;
      this.userId = "2342384";
      this.item = "WebGoat Sticker Pack";
      this.shippingAddress = "1 Tom Cat Way";
      this.total = 12.99;
    } else if (id.equals("9002")) {
      this.orderId = id;
      this.userId = "2342388";
      this.item = "WebGoat Hoodie";
      this.shippingAddress = "1 Buffalo Bill Ave";
      this.total = 49.99;
    } else {
      // not found
    }
  }

  public Map<String, Object> orderToMap() {
    Map<String, Object> orderMap = new HashMap<>();
    orderMap.put("orderId", this.orderId);
    orderMap.put("userId", this.userId);
    orderMap.put("item", this.item);
    orderMap.put("shippingAddress", this.shippingAddress);
    orderMap.put("total", this.total);
    return orderMap;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  public String getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(String shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }
}
