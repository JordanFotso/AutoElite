package com.vehiclub.api.services.seeder;

import com.vehiclub.api.domain.Vehicule;
import com.vehiclub.api.domain.embeddable.Specifications;
import com.vehiclub.api.domain.enums.VehicleType;
import com.vehiclub.api.domain.VehicleOption;
import com.vehiclub.api.services.VehiculeService;
import com.vehiclub.api.services.factory.ElectriqueFactory;
import com.vehiclub.api.services.factory.EssenceFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Profile("!test") // Ne pas exécuter les seeders lors des tests
public class DataSeeder implements CommandLineRunner {

    private final VehiculeService vehiculeService;
    private final ResourceLoader resourceLoader;

    public DataSeeder(VehiculeService vehiculeService, ResourceLoader resourceLoader) {
        this.vehiculeService = vehiculeService;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vehiculeService.getVehiculesFromIterator().isEmpty()) {
            seedVehicles();
        }
    }

    private void seedVehicles() throws IOException {
        System.out.println("Seeding initial vehicle data...");

        // Véhicule 1: Automobile Essence
        Vehicule autoEssence = new Vehicule();
        autoEssence.setName("Toyota Tundra");
        autoEssence.setType(VehicleType.AUTOMOBILE);
        autoEssence.setBrand("Toyota");
        autoEssence.setModel("Tundra");
        autoEssence.setYear(2023);
        autoEssence.setBasePrice(21000.0);
        autoEssence.setDescription("Finition impeccable, moteur souple, cabine confortable et nombreux choix de modèles. Le big truck de chez Toyota. GPL possible , flex fuel d’origine.");
        autoEssence.setInStockSince(LocalDate.of(2023, 3, 15));
        autoEssence.setOnSale(false);
        Specifications specsEssence = new Specifications();
        specsEssence.setEngine("Essence");
        specsEssence.setPower("381 ch");
        specsEssence.setAcceleration("11.5s (0-100km/h)");
        specsEssence.setTopSpeed("180 km/h");
        autoEssence.setSpecifications(specsEssence);
        autoEssence.setAvailableOptions(Arrays.asList(
            createOption("Pack Confort", 800.0, "Intérieur", Collections.emptyList()),
            createOption("Jantes alliage 17\"", 600.0, "Extérieur", Collections.emptyList())
        ));
        vehiculeService.createVehicule(autoEssence, getBytesFromResource("Toyota-Tundra-75.webp"), "Toyota-Tundra-75.webp", new EssenceFactory());

        // Véhicule 2: Automobile Électrique
        Vehicule autoElectrique = new Vehicule();
        autoElectrique.setName("Model 3");
        autoElectrique.setType(VehicleType.AUTOMOBILE);
        autoElectrique.setBrand("Tesla");
        autoElectrique.setModel("Standard Range Plus");
        autoElectrique.setYear(2024);
        autoElectrique.setBasePrice(45000.0);
        autoElectrique.setDescription("La référence des véhicules électriques performants.");
        autoElectrique.setInStockSince(LocalDate.of(2024, 1, 20));
        autoElectrique.setOnSale(true);
        autoElectrique.setSaleDiscount(4500.0); // 10% de remise
        Specifications specsElectrique = new Specifications();
        specsElectrique.setEngine("Électrique");
        specsElectrique.setPower("283 ch");
        specsElectrique.setAcceleration("5.6s (0-100km/h)");
        specsElectrique.setTopSpeed("225 km/h");
        autoElectrique.setSpecifications(specsElectrique);
        autoElectrique.setAvailableOptions(Arrays.asList(
            createOption("Autopilot Amélioré", 3800.0, "Technologie", Collections.emptyList()),
            createOption("Intérieur Blanc Premium", 1200.0, "Intérieur", Collections.emptyList())
        ));
        vehiculeService.createVehicule(autoElectrique, getBytesFromResource("Model-3-Standard.avif"), "Model-3-Standard.avif", new ElectriqueFactory());

        // Véhicule 3: Scooter
        Vehicule scooter = new Vehicule();
        scooter.setName("NMAX 125");
        scooter.setType(VehicleType.SCOOTER);
        scooter.setBrand("Yamaha");
        scooter.setModel("125");
        scooter.setYear(2023);
        scooter.setBasePrice(3500.0);
        scooter.setDescription("Le scooter urbain par excellence, agile et économique.");
        scooter.setInStockSince(LocalDate.of(2023, 7, 1));
        scooter.setOnSale(false);
        Specifications specsScooter = new Specifications();
        specsScooter.setEngine("Essence");
        specsScooter.setPower("12 ch");
        specsScooter.setAcceleration("nc");
        specsScooter.setTopSpeed("100 km/h");
        scooter.setSpecifications(specsScooter);
        scooter.setAvailableOptions(Arrays.asList(
            createOption("Pare-brise Haut", 150.0, "Accessoire", Collections.emptyList()),
            createOption("Top Case 39L", 200.0, "Accessoire", Collections.emptyList())
        ));
        vehiculeService.createVehicule(scooter, getBytesFromResource("NMAX-125.jpg"), "NMAX-125.jpg", new EssenceFactory());
    }

    private VehicleOption createOption(String name, double price, String category, List<String> incompatibleWith) {
        VehicleOption option = new VehicleOption();
        option.setName(name);
        option.setPrice(price);
        option.setCategory(category);
        option.setIncompatibleWith(incompatibleWith);
        return option;
    }

    private byte[] getBytesFromResource(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:seed-images/" + filename);
        return Files.readAllBytes(resource.getFile().toPath());
    }
}
