use strict;
use warnings;

use Getopt::Std;
use Scalar::Util qw(looks_like_number);

my $scale = 0.3;

my $header = <<'EOH';
\documentclass[border=10pt]{standalone}
\usepackage{tikz}
\begin{document}
EOH

my $footer = <<'EOF';
\end{document}
EOF

our ($opt_f, $opt_e, $opt_s);
getopts("fes:");

if ($#ARGV < 0) {
	print "Usage: perl $0 [-f] <replay file>\n";
	print "\t -f : generate full latex document with header, etc...\n";
	print "\t -s : scale pictures (default 0.3)\n";
	print "\t -e : level exploration mode\n";
	exit;
}

$scale = $opt_s if (defined $opt_s && looks_like_number($opt_s));

my @tmp;

{	# slurp from $filename
	local $/ = undef;

	my $filename = $ARGV[0];
	open(my $fh, '<', $filename)
		or die "Can't open file $filename: $!";
	my $replay = <$fh>;
	close $fh;

	@tmp = split('', $replay);
}

# offset used to display multiple levels
my $offset = 0;

my $map = [];
my $max = 1;
my $btcnt = [];

my $turn = 0;
my $max_discov_turn = 0;
my $discov_turn = [];

for (my $j = 0, my $i = 0; $j <= $#tmp; $j++) {

	if ($tmp[$j] eq 'C') {
		$offset += 25;
	}

	elsif ($tmp[$j] eq 'E') {
		$turn++;
	}

	elsif (($tmp[$j] eq 'g') && ($j + 5 <= $#tmp)) {
		my $glyph = join('', @tmp[$j+1 .. $j+5]);
		my ($y, $x, $g, $c) = unpack("CCaS", $glyph);

		$x += $offset;

		if ($g ne '@') {
			$map->[$x]->[$y] = $g;
			if (! defined $discov_turn->[$x]->[$y]) {
				$max_discov_turn = $turn;
				$discov_turn->[$x]->[$y] = $turn;
			}
		}
		
		# codes below 400 with @ as a glyph are codes used for the player
		elsif ($c < 400) {
			$btcnt->[$x]->[$y] += 1;
			$max = ($max > $btcnt->[$x]->[$y]) ? $max : $btcnt->[$x]->[$y];
		}

		$j += 5;
	}
}

if (defined $opt_f) {
	print $header;
}

print '\begin{tikzpicture}', "[scale=$scale]\n";



for my $x (0 .. $#{$map}) {
	my $line = $map->[$x];
	for my $y (0 .. $#{$line}) {
		my $color;
		my $glyph = $line->[$y];

		if (defined $opt_e) {
			my $cnt = $discov_turn->[$x]->[$y];
			$color = (defined $cnt)
				? int(100 * $cnt / $max_discov_turn)
				: undef;
		}

		else {
			my $cnt = $btcnt->[$x]->[$y];
			$color = (defined $cnt)
				? int(100 * $cnt / $max)
				: undef;
		}

		node($x, $y, $glyph, $color) if (defined $glyph);
	}
}

print '\end{tikzpicture}', "\n";

if (defined $opt_f) {
	print $footer;
}


sub node {
	my ($x, $y, $label, $color) = @_;
	# Don't forget the rotation!
	print '\node ';

	if (defined $color) {
		print "[fill=red!$color!yellow] ";
	}
	
	print "at ($y, -$x) {\\verb!$label!};\n";
}
