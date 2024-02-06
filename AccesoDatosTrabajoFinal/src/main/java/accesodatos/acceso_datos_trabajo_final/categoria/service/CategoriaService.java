package accesodatos.acceso_datos_trabajo_final.categoria.service;

import accesodatos.acceso_datos_trabajo_final.categoria.domain.Categoria;
import accesodatos.acceso_datos_trabajo_final.categoria.model.CategoriaDTO;
import accesodatos.acceso_datos_trabajo_final.categoria.repos.CategoriaRepository;
import accesodatos.acceso_datos_trabajo_final.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(final CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> findAll() {
        final List<Categoria> categorias = categoriaRepository.findAll(Sort.by("categoriaId"));
        return categorias.stream()
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .toList();
    }

    public CategoriaDTO get(final Integer categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoriaDTO categoriaDTO) {
        final Categoria categoria = new Categoria();
        mapToEntity(categoriaDTO, categoria);
        return categoriaRepository.save(categoria).getCategoriaId();
    }

    public void update(final Integer categoriaId, final CategoriaDTO categoriaDTO) {
        final Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoriaDTO, categoria);
        categoriaRepository.save(categoria);
    }

    public void delete(final Integer categoriaId) {
        categoriaRepository.deleteById(categoriaId);
    }

    private CategoriaDTO mapToDTO(final Categoria categoria, final CategoriaDTO categoriaDTO) {
        categoriaDTO.setCategoriaId(categoria.getCategoriaId());
        categoriaDTO.setNombre(categoria.getNombre());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        return categoriaDTO;
    }

    private Categoria mapToEntity(final CategoriaDTO categoriaDTO, final Categoria categoria) {
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        return categoria;
    }

}
