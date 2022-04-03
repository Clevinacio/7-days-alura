package utils;

import domain.Movie;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class HTMLGenerator {
    Writer writer;

    public HTMLGenerator(Writer writer) {
        this.writer = writer;
    }

    public void generate(List<Movie> movies) throws IOException {
        String head = mountHead();
        String footer = mountFooter();

        writer.write(head);

        movies.forEach((movie -> {
            try {
                writer.write(
                        """
                            <div class="col-sm-3 col-lg-2">
                                <div class="card" style="background-color: rgb(158, 157, 157); color: white; margin-top: 5px;">
                                    <img src="%s"
                                        class="card-img-top" alt="Capa %s">
                                    <div class="card-body">
                                        <h5 class="card-title">%s</h5>
                                        <p class="card-text">Nota: %s - Ano: %s</p>
                                    </div>
                                </div>
                            </div>
                            """.formatted(movie.urlImage(), movie.title(), movie.title(), movie.rating(), movie.year())

                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        writer.write(footer);
    }

    private String mountHead(){
        return """
                 <!DOCTYPE html>
                 <html lang="en">
                                
                 <head>
                     <meta charset="UTF-8">
                     <meta http-equiv="X-UA-Compatible" content="IE=edge">
                     <meta name="viewport" content="width=device-width, initial-scale=1.0">
                     <title>Top 250 Movies list | Alura</title>
                                
                     <!-- Bootstrap style -->
                     <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
                         integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
                 </head>
                                
                 <body >
                 <div class="container-fluid">
                         <div class="row">
                """;
    }

    private String mountFooter(){
        return """
                    </div>
                 </div>
                 <!-- JS Bootstrap -->
                     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
                         integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
                         crossorigin="anonymous"></script>
                     <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
                         integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
                         crossorigin="anonymous"></script>
                     <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
                         integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
                         crossorigin="anonymous"></script>
                 </body>
                                
                 </html>
                """;
    }
}
