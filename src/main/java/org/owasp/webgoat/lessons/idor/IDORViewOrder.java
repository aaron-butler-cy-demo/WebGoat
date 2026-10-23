/*
 * SPDX-FileCopyrightText: Copyright © 2026 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.idor;

import java.util.Map;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IDORViewOrder implements AssignmentEndpoint {

  @GetMapping(
      path = "/IDOR/order/{orderId}",
      produces = {"application/json"})
  @ResponseBody
  public Map<String, Object> viewOrder(@PathVariable("orderId") String orderId) {
    // NOTE: intentionally vulnerable - no check that the requesting user owns this order
    // (missing horizontal access control / IDOR), so any authenticated user can fetch any
    // order by guessing or enumerating orderId.
    Order order = new Order(orderId);
    return order.orderToMap();
  }
}
