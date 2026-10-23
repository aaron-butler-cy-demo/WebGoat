/*
 * SPDX-FileCopyrightText: Copyright © 2024 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.commandinjection;

import org.owasp.webgoat.container.lessons.Category;
import org.owasp.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

@Component
public class CommandInjection extends Lesson {

  @Override
  public Category getDefaultCategory() {
    return Category.A3;
  }

  @Override
  public String getTitle() {
    return "command-injection.title";
  }
}
