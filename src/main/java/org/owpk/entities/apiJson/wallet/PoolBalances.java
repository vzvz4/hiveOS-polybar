package org.owpk.entities.apiJson.wallet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.owpk.entities.AbsComponent;
import org.owpk.entities.jsonConfig.JsonConfig;
import org.owpk.entities.jsonConfig.JsonData;
import org.owpk.utils.Resources;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"pool", "valueUsd"})
public class PoolBalances extends AbsComponent {

   private String pool;
   @JsonProperty
   private Double value;
   @JsonProperty(value = "value_usd")
   private Double valueUsd;
   @JsonProperty(value = "value_fiat")
   private Double valueFiat;
   @JsonProperty
   private String status;

   public PoolBalances(String pool, Double value, Double valueUsd, Double valueFiat) {
      this.pool = pool;
      this.value = value;
      this.valueUsd = valueUsd;
      this.valueFiat = valueFiat;
   }

   @Override
   public void execute(JsonConfig jsonConfig) {
      printFieldsToShow(jsonConfig);
   }
}
