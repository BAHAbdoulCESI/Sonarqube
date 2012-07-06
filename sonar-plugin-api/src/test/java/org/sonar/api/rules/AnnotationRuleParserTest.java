/*
 * Sonar, open source software quality management tool.
 * Copyright (C) 2008-2012 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * Sonar is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * Sonar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Sonar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.api.rules;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.api.PropertyType;
import org.sonar.check.IsoCategory;
import org.sonar.check.Priority;

import java.util.Collections;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class AnnotationRuleParserTest {
  @org.junit.Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void ruleWithProperty() {
    List<Rule> rules = parseAnnotatedClass(RuleWithProperty.class);
    assertThat(rules).hasSize(1);
    Rule rule = rules.get(0);
    assertThat(rule.getKey()).isEqualTo("foo");
    assertThat(rule.getName()).isEqualTo("bar");
    assertThat(rule.getDescription()).isEqualTo("Foo Bar");
    assertThat(rule.getSeverity()).isEqualTo(RulePriority.BLOCKER);
    assertThat(rule.getParams()).hasSize(1);
    RuleParam prop = rule.getParam("property");
    assertThat(prop.getKey()).isEqualTo("property");
    assertThat(prop.getDescription()).isEqualTo("Ignore ?");
    assertThat(prop.getDefaultValue()).isEqualTo("false");
    assertThat(prop.getType()).isEqualTo(PropertyType.STRING.name());
  }

  @Test
  public void ruleWithIntegerProperty() {
    List<Rule> rules = parseAnnotatedClass(RuleWithIntegerProperty.class);

    RuleParam prop = rules.get(0).getParam("property");
    assertThat(prop.getDescription()).isEqualTo("Max");
    assertThat(prop.getDefaultValue()).isEqualTo("12");
    assertThat(prop.getType()).isEqualTo(PropertyType.INTEGER.name());
  }

  @Test
  public void ruleWithTextProperty() {
    List<Rule> rules = parseAnnotatedClass(RuleWithTextProperty.class);

    RuleParam prop = rules.get(0).getParam("property");
    assertThat(prop.getDescription()).isEqualTo("text");
    assertThat(prop.getDefaultValue()).isEqualTo("Long text");
    assertThat(prop.getType()).isEqualTo(PropertyType.TEXT.name());
  }

  @Test
  public void should_reject_invalid_prroperty_types() {
    exception.expect(IllegalArgumentException.class);
    exception.expectMessage("Invalid property type [INVALID]");

    parseAnnotatedClass(RuleWithInvalidPropertyType.class);
  }

  @Test
  public void should_recognize_type() {
    assertThat(AnnotationRuleParser.guessType(Integer.class)).isEqualTo(PropertyType.INTEGER);
    assertThat(AnnotationRuleParser.guessType(int.class)).isEqualTo(PropertyType.INTEGER);
    assertThat(AnnotationRuleParser.guessType(Float.class)).isEqualTo(PropertyType.FLOAT);
    assertThat(AnnotationRuleParser.guessType(float.class)).isEqualTo(PropertyType.FLOAT);
    assertThat(AnnotationRuleParser.guessType(Boolean.class)).isEqualTo(PropertyType.BOOLEAN);
    assertThat(AnnotationRuleParser.guessType(boolean.class)).isEqualTo(PropertyType.BOOLEAN);
    assertThat(AnnotationRuleParser.guessType(String.class)).isEqualTo(PropertyType.STRING);
    assertThat(AnnotationRuleParser.guessType(Object.class)).isEqualTo(PropertyType.STRING);
  }

  @Test
  public void ruleWithoutNameNorDescription() {
    List<Rule> rules = parseAnnotatedClass(RuleWithoutNameNorDescription.class);
    assertThat(rules).hasSize(1);
    Rule rule = rules.get(0);
    assertThat(rule.getKey()).isEqualTo("foo");
    assertThat(rule.getSeverity()).isEqualTo(RulePriority.MAJOR);
    assertThat(rule.getName()).isNull();
    assertThat(rule.getDescription()).isNull();
  }

  @Test
  public void ruleWithoutKey() {
    List<Rule> rules = parseAnnotatedClass(RuleWithoutKey.class);
    assertThat(rules).hasSize(1);
    Rule rule = rules.get(0);
    assertThat(rule.getKey()).isEqualTo(RuleWithoutKey.class.getCanonicalName());
    assertThat(rule.getName()).isEqualTo("foo");
    assertThat(rule.getDescription()).isNull();
    assertThat(rule.getSeverity()).isEqualTo(RulePriority.MAJOR);
  }

  @Test
  public void supportDeprecatedAnnotations() {
    List<Rule> rules = parseAnnotatedClass(Check.class);
    assertThat(rules).hasSize(1);
    Rule rule = rules.get(0);
    assertThat(rule.getKey()).isEqualTo(Check.class.getCanonicalName());
    assertThat(rule.getName()).isEqualTo(Check.class.getCanonicalName());
    assertThat(rule.getDescription()).isEqualTo("Deprecated check");
    assertThat(rule.getSeverity()).isEqualTo(RulePriority.BLOCKER);
  }

  private List<Rule> parseAnnotatedClass(Class annotatedClass) {
    return new AnnotationRuleParser().parse("repo", Collections.singleton(annotatedClass));
  }

  @org.sonar.check.Rule(name = "foo")
  static class RuleWithoutKey {
  }

  @org.sonar.check.Rule(key = "foo")
  static class RuleWithoutNameNorDescription {
  }

  @org.sonar.check.Rule(key = "foo", name = "bar", description = "Foo Bar", priority = Priority.BLOCKER)
  static class RuleWithProperty {
    @org.sonar.check.RuleProperty(description = "Ignore ?", defaultValue = "false")
    public String property;
  }

  @org.sonar.check.Rule(key = "foo", name = "bar", description = "Foo Bar", priority = Priority.BLOCKER)
  static class RuleWithIntegerProperty {
    @org.sonar.check.RuleProperty(description = "Max", defaultValue = "12")
    public Integer property;
  }

  @org.sonar.check.Rule(key = "foo", name = "bar", description = "Foo Bar", priority = Priority.BLOCKER)
  static class RuleWithTextProperty {
    @org.sonar.check.RuleProperty(description = "text", defaultValue = "Long text", type = "TEXT")
    public String property;
  }

  @org.sonar.check.Rule(key = "foo", name = "bar", description = "Foo Bar", priority = Priority.BLOCKER)
  static class RuleWithInvalidPropertyType {
    @org.sonar.check.RuleProperty(description = "text", defaultValue = "Long text", type = "INVALID")
    public String property;
  }

  @org.sonar.check.Check(description = "Deprecated check", priority = Priority.BLOCKER, isoCategory = IsoCategory.Maintainability)
  static class Check {
  }
}
