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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.text.Normalizer;
import java.nio.charset.StandardCharsets;

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

        // Véhicule 1: Automobile Essence (Toyota Tundra)
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
        MultipartFile imageEssence = loadImageAsMultipartFile("Toyota-Tundra-75.webp", "image/webp");
        vehiculeService.createVehicule(autoEssence, imageEssence, new EssenceFactory());

        // Véhicule 2: Automobile Électrique (Tesla Model 3)
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
        MultipartFile imageElectrique = loadImageAsMultipartFile("Model-3-Standard.avif", "image/avif");
        vehiculeService.createVehicule(autoElectrique, imageElectrique, new ElectriqueFactory());

        // Véhicule 3: Scooter (Yamaha NMAX 125)
        Vehicule scooterEssence = new Vehicule();
        scooterEssence.setName("NMAX 125");
        scooterEssence.setType(VehicleType.SCOOTER);
        scooterEssence.setBrand("Yamaha");
        scooterEssence.setModel("125");
        scooterEssence.setYear(2023);
        scooterEssence.setBasePrice(3500.0);
        scooterEssence.setDescription("Le scooter urbain par excellence, agile et économique.");
        scooterEssence.setInStockSince(LocalDate.of(2023, 7, 1));
        scooterEssence.setOnSale(false);
        Specifications specsScooterEssence = new Specifications();
        specsScooterEssence.setEngine("Essence");
        specsScooterEssence.setPower("12 ch");
        specsScooterEssence.setAcceleration("nc");
        specsScooterEssence.setTopSpeed("100 km/h");
        scooterEssence.setSpecifications(specsScooterEssence);
        scooterEssence.setAvailableOptions(Arrays.asList(
            createOption("Pare-brise Haut", 150.0, "Accessoire", Collections.emptyList()),
            createOption("Top Case 39L", 200.0, "Accessoire", Collections.emptyList())
        ));
        MultipartFile imageScooterEssence = loadImageAsMultipartFile("NMAX-125.jpg", "image/jpeg");
        vehiculeService.createVehicule(scooterEssence, imageScooterEssence, new EssenceFactory());

        // NOUVEAU VÉHICULE 4: Voiture Sportive (Porsche 911)
        Vehicule porsche911 = new Vehicule();
        porsche911.setName("911 Carrera S");
        porsche911.setType(VehicleType.AUTOMOBILE);
        porsche911.setBrand("Porsche");
        porsche911.setModel("911");
        porsche911.setYear(2024);
        porsche911.setBasePrice(125000.0);
        porsche911.setDescription("L'icône de la performance et du design allemand.");
        porsche911.setInStockSince(LocalDate.of(2024, 2, 1));
        porsche911.setOnSale(true);
        porsche911.setSaleDiscount(12500.0); // 10% de remise
        Specifications specsPorsche = new Specifications();
        specsPorsche.setEngine("Essence");
        specsPorsche.setPower("450 ch");
        specsPorsche.setAcceleration("3.7s (0-100km/h)");
        specsPorsche.setTopSpeed("308 km/h");
        porsche911.setSpecifications(specsPorsche);
        porsche911.setAvailableOptions(Arrays.asList(
            createOption("Pack Sport Chrono", 2500.0, "Performance", Collections.emptyList()),
            createOption("Sièges Sport Adaptatifs Plus", 3000.0, "Intérieur", Collections.emptyList())
        ));
        MultipartFile imagePorsche = loadImageAsMultipartFile("porsche911.jpeg", "image/jpeg");
        vehiculeService.createVehicule(porsche911, imagePorsche, new EssenceFactory());

        // NOUVEAU VÉHICULE 5: Voiture Économique (Peugeot 208)
        Vehicule peugeot208 = new Vehicule();
        peugeot208.setName("208 Active Pack");
        peugeot208.setType(VehicleType.AUTOMOBILE);
        peugeot208.setBrand("Peugeot");
        peugeot208.setModel("208");
        peugeot208.setYear(2023);
        peugeot208.setBasePrice(18000.0);
        peugeot208.setDescription("Une citadine polyvalente et agréable à conduire.");
        peugeot208.setInStockSince(LocalDate.of(2023, 10, 1));
        peugeot208.setOnSale(false);
        Specifications specsPeugeot = new Specifications();
        specsPeugeot.setEngine("Essence");
        specsPeugeot.setPower("75 ch");
        specsPeugeot.setAcceleration("13.2s (0-100km/h)");
        specsPeugeot.setTopSpeed("170 km/h");
        peugeot208.setSpecifications(specsPeugeot);
        peugeot208.setAvailableOptions(Arrays.asList(
            createOption("Caméra de recul", 350.0, "Sécurité", Collections.emptyList()),
            createOption("Peinture spéciale", 600.0, "Extérieur", Collections.emptyList())
        ));
        MultipartFile imagePeugeot = loadImageAsMultipartFile("peugeot208.webp", "image/webp");
        vehiculeService.createVehicule(peugeot208, imagePeugeot, new EssenceFactory());

        // NOUVEAU VÉHICULE 6: Scooter Électrique (Silence S01)
        Vehicule silenceS01 = new Vehicule();
        silenceS01.setName("Silence S01");
        silenceS01.setType(VehicleType.SCOOTER);
        silenceS01.setBrand("Silence");
        silenceS01.setModel("S01");
        silenceS01.setYear(2024);
        silenceS01.setBasePrice(6900.0);
        silenceS01.setDescription("Le scooter électrique haute performance, idéal pour la ville.");
        silenceS01.setInStockSince(LocalDate.of(2024, 3, 1));
        silenceS01.setOnSale(true);
        silenceS01.setSaleDiscount(690.0); // 10% de remise
        Specifications specsSilence = new Specifications();
        specsSilence.setEngine("Électrique");
        specsSilence.setPower("11 ch");
        specsSilence.setAcceleration("3.8s (0-50km/h)");
        specsSilence.setTopSpeed("100 km/h");
        silenceS01.setSpecifications(specsSilence);
        silenceS01.setAvailableOptions(Arrays.asList(
            createOption("Top Case Grand Volume", 250.0, "Accessoire", Collections.emptyList()),
            createOption("Prise USB intégrée", 50.0, "Confort", Collections.emptyList())
        ));
        MultipartFile imageSilence = loadImageAsMultipartFile("silences01.webp", "image/webp");
        vehiculeService.createVehicule(silenceS01, imageSilence, new ElectriqueFactory());

        // NOUVEAU VÉHICULE 7: Voiture avec options incompatibles (Audi A4)
        Vehicule audiA4 = new Vehicule();
        audiA4.setName("A4 Avant");
        audiA4.setType(VehicleType.AUTOMOBILE);
        audiA4.setBrand("Audi");
        audiA4.setModel("A4");
        audiA4.setYear(2024);
        audiA4.setBasePrice(42000.0);
        audiA4.setDescription("Berline premium alliant élégance et technologie.");
        audiA4.setInStockSince(LocalDate.of(2024, 4, 10));
        audiA4.setOnSale(false);
        Specifications specsAudi = new Specifications();
        specsAudi.setEngine("Essence");
        specsAudi.setPower("204 ch");
        specsAudi.setAcceleration("7.3s (0-100km/h)");
        specsAudi.setTopSpeed("240 km/h");
        audiA4.setSpecifications(specsAudi);
        audiA4.setAvailableOptions(Arrays.asList(
            createOption("Sièges Sportifs", 1500.0, "Intérieur", Arrays.asList(generateIdFromName("Sièges en Cuir"))),
            createOption("Sièges en Cuir", 2000.0, "Intérieur", Arrays.asList(generateIdFromName("Sièges Sportifs"))),
            createOption("Pack Assistance Route", 1200.0, "Sécurité", Collections.emptyList())
        ));
        MultipartFile imageAudi = loadImageAsMultipartFile("audi-a4.jpeg", "image/jpeg");
        vehiculeService.createVehicule(audiA4, imageAudi, new EssenceFactory());

        // NOUVEAU VÉHICULE 8: Scooter avec options incompatibles (BMW C 400 GT)
        Vehicule bmwC400Gt = new Vehicule();
        bmwC400Gt.setName("C 400 GT");
        bmwC400Gt.setType(VehicleType.SCOOTER);
        bmwC400Gt.setBrand("BMW");
        bmwC400Gt.setModel("C 400 GT");
        bmwC400Gt.setYear(2023);
        bmwC400Gt.setBasePrice(8500.0);
        bmwC400Gt.setDescription("Maxi-scooter pour la ville et les longs trajets.");
        bmwC400Gt.setInStockSince(LocalDate.of(2023, 6, 20));
        bmwC400Gt.setOnSale(true);
        bmwC400Gt.setSaleDiscount(850.0); // 10% de remise
        Specifications specsBMW = new Specifications();
        specsBMW.setEngine("Essence"); // En supposant une version essence du C 400 GT pour l'exemple
        specsBMW.setPower("34 ch");
        specsBMW.setAcceleration("9.5s (0-100km/h)");
        specsBMW.setTopSpeed("139 km/h");
        bmwC400Gt.setSpecifications(specsBMW);
        bmwC400Gt.setAvailableOptions(Arrays.asList(
            createOption("Poignées Chauffantes", 200.0, "Confort", Arrays.asList(generateIdFromName("Protège-Mains"))),
            createOption("Protège-Mains", 150.0, "Confort", Arrays.asList(generateIdFromName("Poignées Chauffantes"))),
            createOption("Connectivity avec Écran TFT", 600.0, "Technologie", Collections.emptyList())
        ));
        MultipartFile imageBMW = loadImageAsMultipartFile("bmw-c400gt.webp", "image/webp");
        vehiculeService.createVehicule(bmwC400Gt, imageBMW, new EssenceFactory());
    }

    private VehicleOption createOption(String name, double price, String category, List<String> incompatibleWith) {
        VehicleOption option = new VehicleOption();
        option.setId(generateIdFromName(name)); // Générer l'ID métier à partir du nom
        option.setName(name);
        option.setPrice(price);
        option.setCategory(category);
        option.setIncompatibleWith(incompatibleWith);
        return option;
    }
    
    private String generateIdFromName(String name) {
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                         .toLowerCase()
                         .replaceAll("[^a-z0-9]+", "-")
                         .replaceAll("^-|-$", "");
    }

    private MultipartFile loadImageAsMultipartFile(String filename, String contentType) throws IOException {
        try {
            Resource resource = resourceLoader.getResource("classpath:seed-images/" + filename);
            if (!resource.exists()) {
                System.err.println("Warning: Seed image not found: " + filename);
                return new MockMultipartFile(filename, filename, contentType, "".getBytes(StandardCharsets.UTF_8)); // Retourne un fichier vide
            }
            byte[] fileContent = Files.readAllBytes(resource.getFile().toPath());
            return new MockMultipartFile(filename, filename, contentType, fileContent);
        } catch (IOException e) {
            System.err.println("Warning: Could not load seed image " + filename + ". " + e.getMessage());
            return new MockMultipartFile(filename, filename, contentType, "".getBytes(StandardCharsets.UTF_8)); // Retourne un fichier vide
        }
    }
}
