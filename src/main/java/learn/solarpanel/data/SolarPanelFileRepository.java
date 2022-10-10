package learn.solarpanel.data;

import learn.solarpanels.model.Sections;
import learn.solarpanels.model.SolarPanel;
import learn.solarpanels.model.SolarPanelMaterial;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SolarPanelFileRepository implements SolarPanelRepository {

    private final String filePath;

    public SolarPanelFileRepository(String filePath) {
        this.filePath = filePath;
    }

    public List<SolarPanel> findAll() throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] fields = line.split(",", -1);
                if (fields.length == 7) {
                    SolarPanel solarPanel = new SolarPanel();
                    solarPanel.setId(Integer.parseInt(fields[0]));
                    solarPanel.setSection(Sections.valueOf(fields[1]));
                    solarPanel.setRow(Integer.parseInt(fields[2]));
                    solarPanel.setColumn(Integer.parseInt(fields[3]));
                    solarPanel.setYearInstalled(Integer.parseInt(fields[4]));
                    solarPanel.setTracking(Boolean.parseBoolean(fields[5]));
                    solarPanel.setMaterial(SolarPanelMaterial.valueOf(fields[6]));
                    result.add(solarPanel);
                }
            }
        } catch (FileNotFoundException ex) {
        }catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(),ex);
        }
        return result;
    }


    public List<SolarPanel> findBySection(Sections section) throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for (SolarPanel solarPanel : findAll()) {
            if (solarPanel.getSection() == section) {
                result.add(solarPanel);
            }
        }return result;

    }



    public SolarPanel findById(int Id) throws DataAccessException {
        for (SolarPanel solarPanel : findAll()) {
            if (solarPanel.getId() == Id) {
                return solarPanel;
            }
        }
        return null;
    }

    public List<SolarPanel> findByMaterial(SolarPanelMaterial material) throws DataAccessException {
        ArrayList<SolarPanel> result = new ArrayList<>();
        for (SolarPanel solarPanel : findAll()) {
            if (solarPanel.getMaterial() == material) {
                result.add(solarPanel);
            }
        }return result;
    }


    public SolarPanel add(SolarPanel solarPanel) throws DataAccessException {
        List<SolarPanel> all = findAll();
        int nextId = 0;
        for (SolarPanel s: all) {
            nextId = Math.max(nextId, s.getId());
        }
        nextId++;
        solarPanel.setId(nextId);
        all.add(solarPanel);
        writeAll(all);
        return solarPanel;
        //save
    }

    public boolean update(SolarPanel solarPanel) throws DataAccessException {
        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++){
            if (Objects.equals(all.get(i).getId(), solarPanel.getId())) {
                all.set(i,solarPanel);
                writeAll(all);
                return true;
            }
        }

        return false;
    }

    public boolean deleteById(int Id) throws DataAccessException {
        List<SolarPanel> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == Id) {
                all.remove(i);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<SolarPanel> solarPanels) throws DataAccessException {
        try(PrintWriter writer = new PrintWriter(filePath)){
            writer.println("Id,Section,row,column,yearInstalled,isTracking,SolarPanelMaterial");
            for (SolarPanel s: solarPanels){
                writer.println(serialize(s));
            }
        } catch (IOException ex) {
            throw new DataAccessException(ex.getMessage(), ex);
        }
    }

    private String serialize(SolarPanel solarPanel) {
        return String.format("%s,%s,%s,%s,%s,%s,%s",
                solarPanel.getId(),
                solarPanel.getSection(),
                solarPanel.getRow(),
                solarPanel.getColumn(),
                solarPanel.getYearInstalled(),
                solarPanel.isTracking(),
                solarPanel.getMaterial());
    }
}


