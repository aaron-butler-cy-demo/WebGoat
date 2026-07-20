/*
 * SPDX-FileCopyrightText: Copyright © 2024 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.insecuredesign;

import org.owasp.webgoat.container.lessons.Category;
import org.owasp.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class InsecureDesign extends Lesson {
  @Override
  public Category getDefaultCategory() {
    return Category.A4;
  }

  @Override
  public String getTitle() {
    return "insecure-design.title";
  }
}
