/*
 * SPDX-FileCopyrightText: Copyright © 2024 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.commandinjection;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({
  "command-injection.hints.task2.1",
  "command-injection.hints.task2.2",
  "command-injection.hints.task2.3"
})
public class CommandInjectionTask2 implements AssignmentEndpoint {

  private final String webGoatHomeDirectory;

  public CommandInjectionTask2(
      @Value("${webgoat.server.directory}") String webGoatHomeDirectory) {
    this.webGoatHomeDirectory = webGoatHomeDirectory;
  }

  @PostMapping("/CommandInjection/dns-lookup")
  @ResponseBody
  public AttackResult dnsLookup(@RequestParam String hostname) {
    // Naive denylist filter: strips semicolons only.
    // Still vulnerable — pipe (|), logical-or (||), and URL-encoded newline (%0a) are not blocked.
    String sanitized = hostname.replace(";", "");

    try {
      boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
      String shellCommand = "nslookup " + sanitized;
      String[] command =
          isWindows
              ? new String[] {"cmd", "/c", shellCommand}
              : new String[] {"sh", "-c", shellCommand};

      String output = executeCommand(command);
      String flag =
          Files.readString(Path.of(webGoatHomeDirectory, "CommandInjection", "flag.txt")).trim();

      if (output.contains(flag)) {
        return success(this).feedback("command-injection.success").output(output).build();
      }
      return failed(this)
          .feedback("command-injection.keep-trying")
          .output(output.isEmpty() ? "(no output)" : output)
          .build();

    } catch (Exception e) {
      return failed(this).output(e.getMessage()).build();
    }
  }

  private String executeCommand(String[] command) throws IOException, InterruptedException {
    var pb = new ProcessBuilder(command).redirectErrorStream(true);
    var process = pb.start();
    try (InputStream is = process.getInputStream()) {
      String output = new String(is.readAllBytes(), StandardCharsets.UTF_8);
      process.waitFor(10, TimeUnit.SECONDS);
      return output.length() > 4000 ? output.substring(0, 4000) + "\n[output truncated]" : output;
    }
  }
}
