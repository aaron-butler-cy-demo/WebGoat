/*
 * SPDX-FileCopyrightText: Copyright © 2024 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.insecuredesign;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({
  "insecure-design.hints.task1.1",
  "insecure-design.hints.task1.2",
  "insecure-design.hints.task1.3"
})
public class InsecureDesignTask1 implements AssignmentEndpoint {

  // Intentionally NOT used — the server should look up the price here instead of trusting the
  // client-supplied value.
  static final double CATALOG_PRICE = 29.99;

  @PostMapping("/InsecureDesign/task1")
  @ResponseBody
  public AttackResult purchase(
      @RequestParam String productName, @RequestParam double price) {

    // Vulnerable: trusts the price submitted by the client instead of looking it up server-side.
    if (price <= 0.0) {
      return success(this)
          .feedback("insecure-design.task1.success")
          .feedbackArgs(productName, price)
          .build();
    }
    return failed(this)
        .feedback("insecure-design.task1.failed")
        .feedbackArgs(productName, price)
        .build();
  }
}
