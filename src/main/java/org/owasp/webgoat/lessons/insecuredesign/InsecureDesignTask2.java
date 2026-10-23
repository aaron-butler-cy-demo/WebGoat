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
  "insecure-design.hints.task2.1",
  "insecure-design.hints.task2.2",
  "insecure-design.hints.task2.3"
})
public class InsecureDesignTask2 implements AssignmentEndpoint {

  static final double UNIT_PRICE = 4.99;

  @PostMapping("/InsecureDesign/task2")
  @ResponseBody
  public AttackResult order(@RequestParam int quantity) {

    // Vulnerable: accepts any integer — no check that quantity is positive.
    double total = UNIT_PRICE * quantity;

    if (quantity < 0) {
      return success(this)
          .feedback("insecure-design.task2.success")
          .feedbackArgs(quantity, total)
          .build();
    }
    return failed(this)
        .feedback("insecure-design.task2.failed")
        .feedbackArgs(quantity, total)
        .build();
  }
}
