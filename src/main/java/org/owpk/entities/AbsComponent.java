package org.owpk.entities;

import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.utils.Resources;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbsComponent implements Component {

   @Override
   @SuppressWarnings("unchecked")
   public void execute(List<String> options) {
      Map<String, Object> objGraph = JsonMapper.convert(this, LinkedHashMap.class);
      objGraph.forEach(defaultBiConsumer(options));
   }

   @Override
   public void execute(JsonConfig jsonConfig) {
      printFieldsToShow(jsonConfig);
   }

   protected BiConsumer<String, Object> defaultBiConsumer(List<String> options) {
      return (k, v) -> {
         if (options.size() == 0) {
            defaultOutput(k, v);
         } else if (options.contains(k)) {
            defaultOutput(k, v);
         } else
            maybeOtherConditions(options, k, v);
      };
   }

   @SuppressWarnings("unchecked")
   protected void printFieldsToShow(JsonConfig jsonConfig) {
      final String format = Resources.ConfigReader.getJsonConfig().getJsonAppConfig().getFormat();
      Map<String, Object> objGraph = JsonMapper.convert(this, LinkedHashMap.class);
      objGraph.forEach((k, v) -> {
         if (jsonConfig.getFieldsToShow().contains(k))
            System.out.printf(format, k, v);
      });
   }

   /**
    * if an entity contains other entities or a list of other entities as field, you need to override
    * this method in the inherited class (which should be a component) and specify that field names.
    * For example:
    *
    * @see org.owpk.entities.apiJson.wallet.Wallet
    */
   protected void maybeOtherConditions(List<String> options, String key, Object value) {

   }

   protected void defaultOutput(String key, Object value) {
      final String format = Resources.ConfigReader.getJsonConfig().getJsonAppConfig().getFormat();
      System.out.printf(format, key, value);
   }

   /**
    * parse the statement "object1:obj1_field1:obj1_field2,..."
    */
   protected List<String> parseInheritedOptions(List<String> options, Predicate<String> predicate) {
      return Arrays.stream(
             options.stream()
                    .filter(predicate)
                    .findAny()
                    .get()
                    .split(":"))
             .skip(1)
             .collect(Collectors.toList());
   }

   protected boolean checkIfValuePresent(List<String> options, String value) {
      return options.stream().anyMatch(x -> x.startsWith(value));
   }
}
